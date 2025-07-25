package com.meesam.jetpackshopping.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.view.common.BottomNavigationBar
import com.meesam.jetpackshopping.view.feed.FeedScreen
import com.meesam.jetpackshopping.view.products.ProductScreen
import com.meesam.jetpackshopping.view.profile.ProfileScreen
import com.meesam.jetpackshopping.view.search.SearchScreen

@Composable
fun HomeScreen(mainNavController: NavHostController) {
    // For simpler bottom nav without nested NavHost:
    var currentBottomTabRoute by rememberSaveable { mutableStateOf(AppDestinations.FEED_ROUTE) }
    // Or, if using a nested NavHost for bottom tabs, create a bottomNavController here:
    // val bottomNavController = rememberNavController()

    Scaffold(
        /*floatingActionButton = {
            val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute == AppDestinations.HOME_ROUTE &&
                currentBottomTabRoute != AppDestinations.HOME_SEARCH_ROUTE &&
                currentBottomTabRoute != AppDestinations.PROFILE_ROUTE
            ) {
                FloatingActionButton(
                    onClick = {
                        mainNavController.navigate(AppDestinations.ADD_USER_ROUTE)
                    },
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }

        },*/
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentBottomTabRoute, // or bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                onTabSelected = { route ->
                    currentBottomTabRoute = route
                    // If using bottomNavController:
                    // bottomNavController.navigate(route) { launchSingleTop = true; popUpTo(bottomNavController.graph.startDestinationId){ saveState = true } }
                }
            )
        }
    ) { paddingValues ->
        // Content based on selected bottom tab
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentBottomTabRoute) {
                AppDestinations.PRODUCT_ROUTE -> ProductScreen() {
                    mainNavController.navigate(AppDestinations.editUserRoute(it))
                }
                AppDestinations.HOME_SEARCH_ROUTE -> SearchScreen()
                AppDestinations.PROFILE_ROUTE -> ProfileScreen()
                AppDestinations.FEED_ROUTE -> FeedScreen()
            }
            // Or, if using a nested NavHost:
            // NestedNavHostForBottomTabs(bottomNavController, mainNavController, paddingValues)
        }
    }
}