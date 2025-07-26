package com.meesam.jetpackshopping.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.events.UserProfileEvent
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(onSignOut:()-> Unit) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userProfile by profileViewModel.userProfile.collectAsState()

    when(val result = userProfile){
        is AppState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(result.errorMessage.toString())
            }
        }
       is AppState.Idle -> {}
       is AppState.Loading -> {
           Column(
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               CircularProgressIndicator()
           }
       }
        is AppState.Success -> {
            if(result.data != null){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(result.data.name)
                    Text(result.data.email)
                    Button(onClick = onSignOut) {
                       Text("Logout Out")
                    }
                }
            }
        }
    }
}