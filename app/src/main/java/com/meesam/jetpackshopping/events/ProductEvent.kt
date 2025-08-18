package com.meesam.jetpackshopping.events

sealed class ProductEvent {
    data class LoadProductById(val id: String): ProductEvent()
}