package com.meesam.jetpackshopping.viewmodel

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
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository):
    ViewModel() {

    private val _registerUiState = MutableStateFlow<AppState<AuthResult>>(AppState.Idle)
    val registerUiState: StateFlow<AppState<AuthResult>> = _registerUiState.asStateFlow()

        var name : String by mutableStateOf("")
            private set
        var email : String by mutableStateOf("")
            private set
        var password: String by mutableStateOf("")
            private set
        var confirmPassword : String by mutableStateOf("")
            private set

    fun onEvent(events: UserRegistrationEvents){
         when(events){
             is UserRegistrationEvents.onConfirmPasswordChange -> {
                 confirmPassword = events.confirmPassword
             }
             is UserRegistrationEvents.onEmailChange -> {
                 email = events.email
             }
             is UserRegistrationEvents.onNameChange -> {
                 name = events.name
             }
             is UserRegistrationEvents.onPasswordChange -> {
                 password = events.password
             }
             UserRegistrationEvents.onRegisterClick -> {
                 userRegistration()
             }
         }
    }

    private fun userRegistration(){
        _registerUiState.value = AppState.Loading
        viewModelScope.launch {
            val result = authRepository.createUserWithEmailAndPassword(email, password)
            if(result == null){
                _registerUiState.value = AppState.Error("Something went wrong")
            }else{
                val user = User(
                    name = name,
                    userid = result.user?.uid ?: ""
                )
                val createdUser = authRepository.addUser(user)
                if (createdUser.isSuccess){
                    _registerUiState.value = AppState.Success(result)
                }
            }
        }
    }




}