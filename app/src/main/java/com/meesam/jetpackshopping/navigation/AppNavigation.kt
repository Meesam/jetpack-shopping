package com.meesam.jetpackshopping.navigation

object AppDestinations {
    const val USER_LIST_ROUTE = "user_list"
    const val ADD_USER_ROUTE = "add_user"
    const val EDIT_USER_SCREEN_ROUTE = "edit_user"
    const val HOME_SEARCH_ROUTE ="search"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home"

    const val FEED_ROUTE = "feed"
    const val PROFILE_ROUTE = "profile"
    const val PRODUCT_ROUTE="product_list"
    const val EDIT_USER_ID_KEY = "id"
    const val EDIT_USER_ROUTE_PATTERN = "$EDIT_USER_SCREEN_ROUTE/{$EDIT_USER_ID_KEY}"
    fun editUserRoute(id: Long) = "$EDIT_USER_SCREEN_ROUTE/$id"

}