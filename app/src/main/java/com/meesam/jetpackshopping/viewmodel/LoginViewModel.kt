package com.meesam.jetpackshopping.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.meesam.jetpackshopping.events.UserLoginEvents
import com.meesam.jetpackshopping.repository.AuthRepository
import com.meesam.jetpackshopping.states.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginUiState = MutableStateFlow<AppState<String>>(AppState.Idle)
    val loginUiState: StateFlow<AppState<String>> = _loginUiState.asStateFlow()

    var email: String by mutableStateOf("")
        private set
    var password: String by mutableStateOf("")
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: UserLoginEvents) {
        when (event) {
            is UserLoginEvents.onEmailChange -> {
                email = event.email
                isEmailValid(showError = true)
            }

            is UserLoginEvents.onPasswordChange -> {
                password = event.password
                isPasswordValid(showError = true)
            }

            is UserLoginEvents.onLoginClick -> {
                signInUser()
            }

            is UserLoginEvents.reset -> {
                reset()
            }
        }
    }

   private fun isEmailValid(showError: Boolean = false): Boolean{
        if(email.isBlank()){
            if (showError) emailError = "Email cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (showError) emailError = "Invalid email address"
            return false
        }
        emailError = null
        return true
    }

    private fun isPasswordValid(showError: Boolean = false): Boolean{
        if(password.isBlank()){
            if (showError) passwordError = "Password cannot be empty"
            return false
        }
        passwordError = null
        return true
    }

    private fun isFormValid(): Boolean{
       val emailValid = isEmailValid(showError = true)
       val passwordValid = isPasswordValid(showError = true)
        return emailValid && passwordValid
    }

    private fun signInUser() {
        if(!isFormValid()){
            return
        }
        viewModelScope.launch {
            _loginUiState.value = AppState.Loading
            val result = authRepository.signInWithEmailAndPassword(email, password)
            if(result.isFailure){
                _loginUiState.value = AppState.Error(result.toString())
            }else{
                _loginUiState.value = AppState.Success(result.toString())
            }
        }
    }

   private fun reset(){
        email = ""
        password = ""
       emailError = null
       passwordError = null
       _loginUiState.value = AppState.Idle
    }

}