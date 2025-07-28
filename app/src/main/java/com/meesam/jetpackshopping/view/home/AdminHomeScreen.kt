package com.meesam.jetpackshopping.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.view.category.AdminCategoryScreen
import com.meesam.jetpackshopping.view.common.BottomNavigationBar
import com.meesam.jetpackshopping.view.feed.AdminDashboardScreen
import com.meesam.jetpackshopping.view.feed.FeedScreen
import com.meesam.jetpackshopping.view.products.ProductScreen
import com.meesam.jetpackshopping.view.profile.ProfileScreen
import com.meesam.jetpackshopping.view.search.SearchScreen

@Composable
fun AdminHomeScreen(mainNavController: NavController, isAdminLoggedIn: Boolean, onSignOut:()-> Unit) {
    var currentBottomTabRoute by rememberSaveable { mutableStateOf(AppDestinations.ADMIN_DASHBOARD) }
    Scaffold( bottomBar = {
        BottomNavigationBar(
            currentRoute = currentBottomTabRoute,
            isAdminLoggedIn = isAdminLoggedIn,
            onTabSelected = { route ->
                currentBottomTabRoute = route
                // If using bottomNavController:
                // bottomNavController.navigate(route) { launchSingleTop = true; popUpTo(bottomNavController.graph.startDestinationId){ saveState = true } }
            },
        )
    }) {
            paddingValues ->
        // Content based on selected bottom tab
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentBottomTabRoute) {
                AppDestinations.ADMIN_PRODUCT -> ProductScreen() {
                    mainNavController.navigate(AppDestinations.editUserRoute(it))
                }
                AppDestinations.ADMIN_CATEGORY -> AdminCategoryScreen()
                AppDestinations.PROFILE_ROUTE -> ProfileScreen( onSignOut=onSignOut)
                AppDestinations.ADMIN_DASHBOARD -> AdminDashboardScreen()
            }
        }
    }
}