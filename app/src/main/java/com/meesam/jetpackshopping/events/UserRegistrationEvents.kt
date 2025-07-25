package com.meesam.jetpackshopping.events

sealed class UserRegistrationEvents {
  data class onNameChange(val name: String) : UserRegistrationEvents()
  data class onEmailChange(val email: String) : UserRegistrationEvents()
  data class onPasswordChange(val password: String) : UserRegistrationEvents()
  data class onConfirmPasswordChange(val confirmPassword: String) : UserRegistrationEvents()
  data object onRegisterClick : UserRegistrationEvents()

  data object reset: UserRegistrationEvents()

}