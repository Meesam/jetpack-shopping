package com.meesam.jetpackshopping.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.meesam.jetpackshopping.events.UserProfileEvent
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.ui.theme.JetpackShoppingTheme
import com.meesam.jetpackshopping.view.auth.UserLoginScreen
import com.meesam.jetpackshopping.view.auth.UserRegistrationScreen
import com.meesam.jetpackshopping.view.home.AdminHomeScreen
import com.meesam.jetpackshopping.view.home.HomeScreen
import com.meesam.jetpackshopping.view.onboarding.OnBoardingScreen
import com.meesam.jetpackshopping.view.products.ProductDetailScreen
import com.meesam.jetpackshopping.view.products.ProductScreen
import com.meesam.jetpackshopping.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackShoppingTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   AppNavigation()
               }

            }
        }
    }
}

@Composable
fun AppNavigation() {
    val mainNavController = rememberNavController()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val isLoadingInitialUser by profileViewModel.isLoadingInitialUser.collectAsState()
    val isUserLoggedIn by profileViewModel.isUserLoggedIn.collectAsState()


    if(isLoadingInitialUser){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else {
        // Determine the start destination AFTER the initial loading is complete
        val startDestination = if (isUserLoggedIn) {
            AppDestinations.HOME_ROUTE
        } else {
            AppDestinations.ONBOARDING_ROUTE
        }

        NavHost(
            navController = mainNavController,
            startDestination = startDestination,
        ) {

            composable(AppDestinations.ONBOARDING_ROUTE) {
                OnBoardingScreen(onNavigateToLogin = {
                    mainNavController.navigate(AppDestinations.LOGIN_ROUTE)
                }, onNavigateToRegister = {
                    mainNavController.navigate(AppDestinations.REGISTER_ROUTE)
                })
            }

            composable(AppDestinations.LOGIN_ROUTE) {
                UserLoginScreen(
                    onLoginSuccess = {
                        mainNavController.navigate(AppDestinations.HOME_ROUTE) {
                            popUpTo(AppDestinations.LOGIN_ROUTE) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        mainNavController.navigate(AppDestinations.REGISTER_ROUTE)
                    }
                )
            }

            composable(AppDestinations.HOME_ROUTE) {
                // Home screen will manage its own content based on bottom nav selection
                HomeScreen(mainNavController = mainNavController, isAdminLoggedIn = false ,onSignOut = {
                    profileViewModel.onEvent(UserProfileEvent.onSignOut)
                    mainNavController.navigate(AppDestinations.LOGIN_ROUTE) {
                        popUpTo(AppDestinations.HOME_ROUTE) { inclusive = true }
                        launchSingleTop = true
                    }
                }) // Pass mainNavController if Profile needs to navigate outside Home
            }

            composable(AppDestinations.ADMIN_HOME) {
                // Home screen will manage its own content based on bottom nav selection
                AdminHomeScreen(mainNavController = mainNavController, isAdminLoggedIn = true, onSignOut = {
                    profileViewModel.onEvent(UserProfileEvent.onSignOut)
                    mainNavController.navigate(AppDestinations.LOGIN_ROUTE) {
                        popUpTo(AppDestinations.ADMIN_HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                }) // Pass mainNavController if Profile needs to navigate outside Home
            }
            composable(AppDestinations.REGISTER_ROUTE) {
                UserRegistrationScreen(
                    onBackToLogin = { mainNavController.popBackStack() }
                )
            }


            composable(AppDestinations.PRODUCT_ROUTE) {
                ProductScreen(
                    onProductClick = {userId ->
                        mainNavController.navigate(AppDestinations.productDetailRoute(userId))
                    }
                )
            }
            composable(
                route = AppDestinations.PRODUCT_DETAIL_ROUTE_PATTERN,
                arguments = listOf(
                    navArgument(AppDestinations.PRODUCT_ID_KEY) {
                        type = NavType.StringType
                    }
                )
            ) {backStackEntry ->
                val productId = backStackEntry.arguments?.getString(AppDestinations.PRODUCT_ID_KEY)
                productId?.let {id->
                    ProductDetailScreen(
                        productId = id,
                        /*onUserAddedSuccessfully = {
                            mainNavController.navigate(AppDestinations.USER_LIST_ROUTE) {
                                popUpTo(AppDestinations.USER_LIST_ROUTE) {
                                    inclusive =
                                        true // This makes userList the new top, removing previous instances
                                }
                            }
                        },*/
                        onGoBack = {
                            mainNavController.popBackStack()
                        })
                }
            }
        }
    }


}

