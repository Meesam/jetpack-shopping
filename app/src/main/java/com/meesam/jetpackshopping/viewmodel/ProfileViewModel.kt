package com.meesam.jetpackshopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.meesam.jetpackshopping.events.UserProfileEvent
import com.meesam.jetpackshopping.model.User
import com.meesam.jetpackshopping.repository.AuthRepository
import com.meesam.jetpackshopping.states.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NavigationCommand {
    data class NavigateTo(val route: String) : NavigationCommand()
    object NavigateBack : NavigationCommand()
}


@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private var _userProfile = MutableStateFlow<AppState<User?>>(AppState.Loading)
    val userProfile: StateFlow<AppState<User?>> = _userProfile.asStateFlow()

    private val _navigationCommand = MutableSharedFlow<NavigationCommand>()
    val navigationCommand = _navigationCommand.asSharedFlow()

    var isUserLoggedIn: StateFlow<Boolean> = userProfile.map { appState ->
        appState is AppState.Success && appState.data != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    var isAdminLoggedIn: StateFlow<Boolean> = userProfile.map { appState ->
        appState is AppState.Success && appState.data != null && appState.data.role == "admin"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isLoadingInitialUser = MutableStateFlow(true)
    val isLoadingInitialUser: StateFlow<Boolean> = _isLoadingInitialUser.asStateFlow()

    private val _showEditProfileBottomSheet  = MutableStateFlow<Boolean>(false)
    val showEditProfileBottomSheet: StateFlow<Boolean> = _showEditProfileBottomSheet.asStateFlow()

    init {
        getUserProfile()
    }

    fun onEvent(event: UserProfileEvent){
        when(event){
            UserProfileEvent.onSignOut -> {
                userLogout()
            }

            UserProfileEvent.onEditClick -> {
                _showEditProfileBottomSheet.value = true
            }

            UserProfileEvent.onDismissSheet -> {
                _showEditProfileBottomSheet.value = false
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