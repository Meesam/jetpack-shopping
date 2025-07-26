package com.meesam.jetpackshopping.model

import com.google.firebase.Timestamp

data class Category(
    val id: String = "",
    val name : String = "",
    val timestamp: Timestamp? = null,
    val isSelected: Boolean = false
)
