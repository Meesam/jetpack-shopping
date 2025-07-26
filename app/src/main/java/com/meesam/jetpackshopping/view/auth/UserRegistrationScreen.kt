package com.meesam.jetpackshopping.view.auth

import android.content.res.Resources
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
import com.meesam.jetpackshopping.events.UserRegistrationEvents
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.viewmodel.RegistrationViewModel


@Composable
fun UserRegistrationScreen(onBackToLogin:()-> Unit) {

    val registrationViewModel : RegistrationViewModel = hiltViewModel()
    val registerUiStatic by registrationViewModel.registerUiState.collectAsState()
    val context = LocalContext.current
    val nameFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        nameFocusRequester.requestFocus()
    }

    LaunchedEffect(registerUiStatic) {
        when(registerUiStatic){
            is AppState.Error -> {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                registrationViewModel.onEvent(UserRegistrationEvents.reset)
            }
            is AppState.Success -> {
                Toast.makeText(context, "You've successfully registered", Toast.LENGTH_LONG).show()
                registrationViewModel.onEvent(UserRegistrationEvents.reset)
                onBackToLogin()
            }
            is AppState.Idle -> {}
            is AppState.Loading -> {}

        }
    }

    if(registerUiStatic is AppState.Loading){
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator()
        }
    }else{
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White)
                .padding(vertical = 50.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(text = "New User Registration", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(id = R.drawable.login_image), contentDescription = "Logo")
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                OutlinedTextField(
                    value = registrationViewModel.name,
                    onValueChange = {
                        registrationViewModel.onEvent(UserRegistrationEvents.onNameChange(it))
                    },
                    placeholder = {
                        Text("Name")
                    },
                    isError = registrationViewModel.nameError != null,
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "User")
                    },
                    modifier = Modifier.fillMaxWidth()
                        .focusRequester(nameFocusRequester)
                )
                if(registrationViewModel.nameError != null){
                    Text(registrationViewModel.nameError.toString(), color = Color.Red ,style = TextStyle(fontSize = 12.sp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = registrationViewModel.email,
                    onValueChange = {
                        registrationViewModel.onEvent(UserRegistrationEvents.onEmailChange(it))
                    },
                    placeholder = {
                        Text("Email")
                    },
                    isError = registrationViewModel.emailError != null,
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "User")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if(registrationViewModel.emailError != null){
                    Text(registrationViewModel.emailError.toString(), color = Color.Red ,style = TextStyle(fontSize = 12.sp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = registrationViewModel.password,
                    onValueChange = {
                        registrationViewModel.onEvent(UserRegistrationEvents.onPasswordChange(it))
                    },
                    placeholder = {
                        Text("Password")
                    },
                    isError = registrationViewModel.passwordError != null,
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "User")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                if(registrationViewModel.passwordError != null){
                    Text(registrationViewModel.passwordError.toString(), color = Color.Red ,style = TextStyle(fontSize = 12.sp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = registrationViewModel.confirmPassword,
                    onValueChange = {
                        registrationViewModel.onEvent(UserRegistrationEvents.onConfirmPasswordChange(it))
                    },
                    placeholder = {
                        Text("Confirm Password")
                    },
                    isError = registrationViewModel.confirmPasswordError != null,
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "User")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                if(registrationViewModel.confirmPasswordError != null){
                    Text(registrationViewModel.confirmPasswordError.toString(), color = Color.Red ,style = TextStyle(fontSize = 12.sp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            registrationViewModel.onEvent(UserRegistrationEvents.onRegisterClick)
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Register", style = TextStyle(fontSize = 20.sp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onBackToLogin,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Back", style = TextStyle(fontSize = 20.sp))
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserRegistrationScreenPrev() {
   // UserRegistrationScreen()
}