package com.meesam.jetpackshopping.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.ui.theme.JetpackShoppingTheme
import com.meesam.jetpackshopping.view.auth.UserLoginScreen
import com.meesam.jetpackshopping.view.auth.UserRegistrationScreen
import com.meesam.jetpackshopping.view.home.HomeScreen
import com.meesam.jetpackshopping.view.products.ProductScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackShoppingTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val mainNavController = rememberNavController()
    NavHost(
        navController = mainNavController,
        startDestination = AppDestinations.LOGIN_ROUTE,
    ) {
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
            HomeScreen(mainNavController = mainNavController) // Pass mainNavController if Profile needs to navigate outside Home
        }
        composable(AppDestinations.REGISTER_ROUTE) {
            UserRegistrationScreen(
                onBackToLogin = { mainNavController.popBackStack() }
            )
        }


        composable(AppDestinations.PRODUCT_ROUTE) {
            ProductScreen(
                onUserEdit = {userId ->
                    mainNavController.navigate(AppDestinations.editUserRoute(userId))
                }
            )
        }
        /*composable(AppDestinations.ADD_USER_ROUTE) {
            AddUserScreen(
                userId = 0,
                onUserAddedSuccessfully = {
                    mainNavController.navigate(AppDestinations.HOME_ROUTE) {
                        popUpTo(AppDestinations.HOME_ROUTE) {
                            inclusive =
                                true // This makes userList the new top, removing previous instances
                        }
                    }
                },
                onGoBack = {
                    mainNavController.popBackStack()
                })
        }*/
        /*composable(
            route = AppDestinations.EDIT_USER_ROUTE_PATTERN,
            arguments = listOf(
                navArgument(AppDestinations.EDIT_USER_ID_KEY) {
                    type = NavType.LongType
                }
            )
        ) {backStackEntry ->
            val userId = backStackEntry.arguments?.getLong(AppDestinations.EDIT_USER_ID_KEY)
            userId?.let {id->
                AddUserScreen(
                    userId = id,
                    onUserAddedSuccessfully = {
                        mainNavController.navigate(AppDestinations.USER_LIST_ROUTE) {
                            popUpTo(AppDestinations.USER_LIST_ROUTE) {
                                inclusive =
                                    true // This makes userList the new top, removing previous instances
                            }
                        }
                    },
                    onGoBack = {
                        mainNavController.popBackStack()
                    })
            }
        }*/
    }
}

