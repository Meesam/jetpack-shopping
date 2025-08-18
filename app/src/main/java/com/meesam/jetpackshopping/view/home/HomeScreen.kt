package com.meesam.jetpackshopping.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.view.common.AppTopBar
import com.meesam.jetpackshopping.view.common.BottomNavigationBar
import com.meesam.jetpackshopping.view.feed.FeedScreen
import com.meesam.jetpackshopping.view.products.ProductScreen
import com.meesam.jetpackshopping.view.profile.ProfileScreen
import com.meesam.jetpackshopping.view.search.SearchScreen

@Composable
fun HomeScreen(mainNavController: NavHostController, isAdminLoggedIn: Boolean, onSignOut:()-> Unit) {
    // For simpler bottom nav without nested NavHost:
    var currentBottomTabRoute by rememberSaveable { mutableStateOf(AppDestinations.FEED_ROUTE) }
    // Or, if using a nested NavHost for bottom tabs, create a bottomNavController here:
    // val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(16.dp)){
                AppTopBar()
            }

        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentBottomTabRoute,
                isAdminLoggedIn = isAdminLoggedIn,
                onTabSelected = { route ->
                    currentBottomTabRoute = route
                    // If using bottomNavController:
                    // bottomNavController.navigate(route) { launchSingleTop = true; popUpTo(bottomNavController.graph.startDestinationId){ saveState = true } }
                },
            )
        }
    ) { paddingValues ->
        // Content based on selected bottom tab
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentBottomTabRoute) {
                AppDestinations.PRODUCT_ROUTE -> ProductScreen() {
                    mainNavController.navigate(AppDestinations.productDetailRoute(it))
                }
                AppDestinations.HOME_SEARCH_ROUTE -> SearchScreen()
                AppDestinations.PROFILE_ROUTE -> ProfileScreen(onSignOut = onSignOut)
                AppDestinations.FEED_ROUTE -> FeedScreen()
            }
        }
    }
}