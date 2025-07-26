package com.meesam.jetpackshopping.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.meesam.jetpackshopping.events.UserRegistrationEvents
import com.meesam.jetpackshopping.model.User
import com.meesam.jetpackshopping.repository.AuthRepository
import com.meesam.jetpackshopping.states.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _registerUiState = MutableStateFlow<AppState<AuthResult>>(AppState.Idle)
    val registerUiState: StateFlow<AppState<AuthResult>> = _registerUiState.asStateFlow()

    var name: String by mutableStateOf("")
        private set
    var email: String by mutableStateOf("")
        private set
    var password: String by mutableStateOf("")
        private set
    var confirmPassword: String by mutableStateOf("")
        private set

    var nameError by mutableStateOf<String?>(null)
        private set
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    fun onEvent(events: UserRegistrationEvents) {
        when (events) {
            is UserRegistrationEvents.onConfirmPasswordChange -> {
                confirmPassword = events.confirmPassword
                comparePassword(showError = true)
            }

            is UserRegistrationEvents.onEmailChange -> {
                email = events.email
                isEmailValid(showError = true)
            }

            is UserRegistrationEvents.onNameChange -> {
                name = events.name
                isNameValid(showError = true)
            }

            is UserRegistrationEvents.onPasswordChange -> {
                password = events.password
                isPasswordValid(showError = true)
            }

            UserRegistrationEvents.onRegisterClick -> {
                if(!isFormValid()){
                    return
                }
                userRegistration()
            }

            UserRegistrationEvents.reset -> {
                reset()
            }
        }
    }

    private fun isNameValid(showError: Boolean = false): Boolean{
        if(name.isBlank()){
            if(showError) nameError = "Name cannot be blank"
            return false
        }
        nameError = null
        return true
    }

    private fun isPasswordValid(showError: Boolean = false): Boolean{
        if(password.isBlank()){
            if(showError) passwordError = "Password cannot be blank"
            return false
        }
        passwordError = null
        return true
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

    private fun comparePassword(showError: Boolean = false): Boolean{
        if(password != confirmPassword){
            if (showError) confirmPasswordError = "Password did not match"
            return false
        }
        confirmPasswordError = null
        return true
    }

    private fun isFormValid(): Boolean{
        val nameValid = isNameValid(showError = true)
        val emailValid = isEmailValid(showError = true)
        val passwordValid = isPasswordValid(showError = true)
        val comparePassword = comparePassword(showError = true)
        return nameValid && emailValid && passwordValid  && comparePassword
    }

    private fun userRegistration() {
        _registerUiState.value = AppState.Loading
        viewModelScope.launch {
            val result = authRepository.createUserWithEmailAndPassword(email, password)
            if (result == null) {
                _registerUiState.value = AppState.Error("Something went wrong")
            } else {
                val user = User(
                    name = name,
                    userid = result.user?.uid ?: ""
                )
                val createdUser = authRepository.addUser(user)
                if (createdUser.isSuccess) {
                    _registerUiState.value = AppState.Success(result)
                }
            }
        }
    }

    private fun reset() {
        name = ""
        email = ""
        password = ""
        confirmPassword = ""
        _registerUiState.value = AppState.Idle
    }


}