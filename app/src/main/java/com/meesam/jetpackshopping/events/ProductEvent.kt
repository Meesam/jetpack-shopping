package com.meesam.jetpackshopping.events

sealed class ProductEvent {
    data class LoadProductById(val id: String): ProductEvent()
    data object ProductCountIncrement: ProductEvent()
    data object ProductCountDecrement: ProductEvent()
}