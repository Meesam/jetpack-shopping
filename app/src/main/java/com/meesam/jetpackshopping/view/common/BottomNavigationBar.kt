package com.meesam.jetpackshopping.view.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.navigation.BottomNavigationItem

@Composable
fun BottomNavigationBar(currentRoute: String?, onTabSelected: (String) -> Unit) {
    val items = listOf(
        BottomNavigationItem("Home", Icons.Filled.Home, AppDestinations.FEED_ROUTE),
        BottomNavigationItem("Explore", Icons.Filled.ShoppingCart, AppDestinations.PRODUCT_ROUTE),
        BottomNavigationItem("Search", Icons.Filled.Search, AppDestinations.HOME_SEARCH_ROUTE),
        BottomNavigationItem("Profile", Icons.Filled.Person, AppDestinations.PROFILE_ROUTE)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = { onTabSelected(item.route) }
            )
        }
    }
}