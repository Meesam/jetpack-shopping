package com.meesam.jetpackshopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.meesam.jetpackshopping.events.UserProfileEvent
import com.meesam.jetpackshopping.model.User
import com.meesam.jetpackshopping.repository.AuthRepository
import com.meesam.jetpackshopping.states.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private var _userProfile = MutableStateFlow<AppState<User?>>(AppState.Loading)
    val userProfile: StateFlow<AppState<User?>> = _userProfile.asStateFlow()

    var isUserLoggedIn: StateFlow<Boolean> = userProfile.map { appState ->
        appState is AppState.Success && appState.data != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isLoadingInitialUser = MutableStateFlow(true)
    val isLoadingInitialUser: StateFlow<Boolean> = _isLoadingInitialUser.asStateFlow()

    init {
        getUserProfile()
    }

    fun onEvent(event: UserProfileEvent){
        when(event){
            UserProfileEvent.onSignOut -> {
                userLogout()
            }
        }
    }

    fun getUserProfile() {
        _isLoadingInitialUser.value = true
        _userProfile.value = AppState.Loading
        viewModelScope.launch {
            try {
                val user = authRepository.getCurrentUserDetails()
                if (user == null) {
                    _userProfile.value = AppState.Error("Something went wrong")
                } else {
                    _userProfile.value = AppState.Success(user)
                }
            }catch (ex: Exception){
                _userProfile.value = AppState.Error("Something went wrong")
            }finally {
                _isLoadingInitialUser.value = false
            }

        }
    }

    fun userLogout(){
        viewModelScope.launch {
            authRepository.signOut()
            _userProfile.value = AppState.Success(null)
        }
    }
}