package com.meesam.jetpackshopping.events

sealed class UserProfileEvent {
  data object onSignOut : UserProfileEvent()
  data object onEditClick : UserProfileEvent()
  data object onDismissSheet: UserProfileEvent()
}