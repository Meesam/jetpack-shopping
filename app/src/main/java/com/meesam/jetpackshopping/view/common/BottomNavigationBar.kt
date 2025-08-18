package com.meesam.jetpackshopping.view.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.navigation.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    isAdminLoggedIn: Boolean,
    onTabSelected: (String) -> Unit,
) {
    val items = if (!isAdminLoggedIn) {
        listOf(
            BottomNavigationItem("Home", Icons.Filled.Home, AppDestinations.FEED_ROUTE),
            BottomNavigationItem(
                "Explore",
                Icons.Filled.ShoppingCart,
                AppDestinations.PRODUCT_ROUTE
            ),
            BottomNavigationItem("Search", Icons.Filled.Search, AppDestinations.HOME_SEARCH_ROUTE),
            BottomNavigationItem("Profile", Icons.Filled.Person, AppDestinations.PROFILE_ROUTE)
        )
    } else {
        listOf(
            BottomNavigationItem("Home", Icons.Filled.Home, AppDestinations.ADMIN_DASHBOARD),
            BottomNavigationItem(
                "Product",
                Icons.Filled.ShoppingCart,
                AppDestinations.ADMIN_PRODUCT
            ),
            BottomNavigationItem("Category", Icons.Filled.Menu, AppDestinations.ADMIN_CATEGORY),
            BottomNavigationItem("Profile", Icons.Filled.Person, AppDestinations.PROFILE_ROUTE)
        )
    }
    NavigationBar(
        containerColor = Color(0XFFFFFFFF),
        contentColor = Color(0XFF31488E),
    ) {
        items.forEach { item ->
            val customIndicatorColor = Color(0XFF31488E)
            val selected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .size(56.dp) // Adjust size as needed
                            .then(
                                if (selected) {
                                    Modifier
                                        .clip(CircleShape) // Or any other Shape
                                        .background(customIndicatorColor)
                                } else Modifier
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = item.title,
                            tint = if (selected) Color(0XFFFFFFFF) else Color(0XFF31488E) // Your selected/unselected icon color
                        )
                    }
                },
                label = {
                    Text(
                        item.title,
                        color = if (selected) Color(0XFF31488E) else Color(0XFFFFFFFF)
                    )
                },
                selected = selected,
                onClick = { onTabSelected(item.route) },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color(0XFFFFFFFF),
                    selectedTextColor = Color(0XFFFFFFFF),
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0XFFFFFFFF),
                    unselectedTextColor = Color(0XFFFFFFFF),
                    disabledIconColor = Color(0XFFFFFFFF),
                    disabledTextColor = Color(0XFFFFFFFF),

                    ),
            )
        }
    }
}