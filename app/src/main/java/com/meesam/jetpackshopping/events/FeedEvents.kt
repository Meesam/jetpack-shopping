package com.meesam.jetpackshopping.events

sealed class FeedEvents {
    data class onCategorySelect(val categoryName: String): FeedEvents()
}