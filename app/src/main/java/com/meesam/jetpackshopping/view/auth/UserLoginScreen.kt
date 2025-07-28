package com.meesam.jetpackshopping.view.auth

import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.R
import com.meesam.jetpackshopping.events.UserLoginEvents
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.viewmodel.LoginViewModel
import com.meesam.jetpackshopping.viewmodel.ProfileViewModel


@Composable
fun UserLoginScreen(onLoginSuccess:()-> Unit, onNavigateToRegister:()-> Unit) {
    val loginViewModel : LoginViewModel = hiltViewModel()
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    val context = LocalContext.current
    val emailFocusRequester = remember { FocusRequester() }

    LaunchedEffect(loginUiState) {
        when(loginUiState){
            is AppState.Error -> {
                Toast.makeText(context, (loginUiState as AppState.Error).errorMessage , Toast.LENGTH_LONG).show()
                loginViewModel.onEvent(UserLoginEvents.reset)

            }
            is AppState.Success -> {
                Toast.makeText(context, "You have logged in successfully", Toast.LENGTH_LONG).show()
                loginViewModel.onEvent(UserLoginEvents.reset)
                onLoginSuccess()
            }
            AppState.Idle -> {}
            AppState.Loading -> {}
        }
    }

    LaunchedEffect(Unit) {
        emailFocusRequester.requestFocus()
    }

    if(loginUiState is AppState.Loading){
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator()
        }
    }else {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White)
                .padding(vertical = 50.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(text = "Welcome to Compose Shopping", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(id = R.drawable.login_image), contentDescription = "Logo")
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                OutlinedTextField(
                    value = loginViewModel.email,
                    onValueChange = {
                        loginViewModel.onEvent(UserLoginEvents.onEmailChange(it))
                    },
                    placeholder = {
                        Text("Email")
                    },
                    isError = loginViewModel.emailError != null,
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "User")
                    },
                    modifier = Modifier.fillMaxWidth()
                        .focusRequester(emailFocusRequester)
                )
                if(loginViewModel.emailError != null){
                    Text(loginViewModel.emailError.toString(), color = Color.Red ,style = TextStyle(fontSize = 12.sp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = loginViewModel.password,
                    onValueChange = {
                        loginViewModel.onEvent(UserLoginEvents.onPasswordChange(it))
                    },
                    placeholder = {
                        Text("Password")
                    },
                    shape = RoundedCornerShape(16.dp),
                    isError = loginViewModel.passwordError != null,
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "User")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                if(loginViewModel.passwordError != null){
                    Text(loginViewModel.passwordError.toString(), color = Color.Red ,style = TextStyle(fontSize = 12.sp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            loginViewModel.onEvent(UserLoginEvents.onLoginClick)
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Login", style = TextStyle(fontSize = 20.sp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onNavigateToRegister,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Register", style = TextStyle(fontSize = 20.sp))
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserLoginScreenPrev() {
   // UserLoginScreen()
}