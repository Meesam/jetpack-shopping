package com.meesam.jetpackshopping.viewmodel

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

    private val _loginUiState = MutableStateFlow<AppState<AuthResult>>(AppState.Idle)
    val loginUiState: StateFlow<AppState<AuthResult>> = _loginUiState.asStateFlow()

    var email: String by mutableStateOf("")
        private set
    var password: String by mutableStateOf("")
        private set

    fun onEvent(event: UserLoginEvents) {
        when (event) {
            is UserLoginEvents.onEmailChange -> {
                email = event.email
            }

            is UserLoginEvents.onPasswordChange -> {
                password = event.password
            }

            is UserLoginEvents.onLoginClick -> {
                signInUser()
            }
        }
    }

    fun signInUser() {
        viewModelScope.launch {
            _loginUiState.value = AppState.Loading
            val result = authRepository.signInWithEmailAndPassword(email, password)
            if(result == null || result.user == null){
                _loginUiState.value = AppState.Error("Something went wrong")
                _loginUiState.value = AppState.Idle
            }else{
                _loginUiState.value = AppState.Success(result)
                _loginUiState.value = AppState.Idle
            }
        }
    }

}