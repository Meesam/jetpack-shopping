package com.meesam.jetpackshopping.events

sealed class UserProfileEvent {
  data object onSignOut : UserProfileEvent()
}