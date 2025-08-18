package com.meesam.jetpackshopping.view.auth

import android.content.res.Resources
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.animation.core.copy
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.R
import com.meesam.jetpackshopping.events.UserLoginEvents
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.ui.theme.JetpackShoppingTheme
import com.meesam.jetpackshopping.viewmodel.LoginViewModel
import com.meesam.jetpackshopping.viewmodel.ProfileViewModel
import kotlin.text.append
import kotlin.text.firstOrNull




@Composable
fun UserLoginScreenUiForPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 60.dp, horizontal = 30.dp)
    ) {

        Text(
            "Login Account", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Please login with registered Account", style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.LightGray
            )
        )
        Spacer(Modifier.height(40.dp))
        Column {
            Text(
                "Email or Phone Number", style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            )
            Spacer(Modifier.height(10.dp))
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                hint = "Enter your email",
                backgroundColor = MaterialTheme.colorScheme.secondary,
                borderColor = Color.Transparent,
                cornerRadius = 12,
                visualTransformation = VisualTransformation.None,
                leadingIcon = Icons.Outlined.Email,
                contentDescription = "Email"

            )
            Spacer(Modifier.height(20.dp))
            Text(
                "Password", style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            )
            Spacer(Modifier.height(10.dp))
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                hint = "Enter your password",
                backgroundColor = MaterialTheme.colorScheme.secondary,
                borderColor = Color.Transparent,
                cornerRadius = 12,
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = Icons.Outlined.Lock,
                contentDescription = "Password"
            )
            Spacer(Modifier.height(20.dp))
            Text(
                "Forgot Password?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(20.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Sign In")
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Or using other method", style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Sign In with Google", color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Sign In with Facebook", color = MaterialTheme.colorScheme.primary)
            }


        }

    }
}

@Composable
fun UserLoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    val context = LocalContext.current
    val emailFocusRequester = remember { FocusRequester() }

    LaunchedEffect(loginUiState) {
        when (loginUiState) {
            is AppState.Error -> {
                Toast.makeText(
                    context,
                    (loginUiState as AppState.Error).errorMessage,
                    Toast.LENGTH_LONG
                ).show()
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

    if (loginUiState is AppState.Loading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFE6EFEF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(vertical = 60.dp, horizontal = 30.dp)
        ) {

            Text(
                "Login Account", style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Please login with registered Account", style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray
                )
            )
            Spacer(Modifier.height(40.dp))
            Column {
                Text(
                    "Email or Phone Number", style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                )
                Spacer(Modifier.height(10.dp))
                CustomTextField(
                    value = loginViewModel.email,
                    onValueChange = {
                        loginViewModel.onEvent(UserLoginEvents.onEmailChange(it))
                    },
                    hint = "Enter your email",
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    borderColor = Color.Transparent,
                    cornerRadius = 12,
                    visualTransformation = VisualTransformation.None,
                    leadingIcon = Icons.Outlined.Email,
                    contentDescription = "Email",
                    isError = loginViewModel.emailError !=null,
                    modifier = Modifier.focusRequester(emailFocusRequester)

                )
                Spacer(Modifier.height(20.dp))
                Text(
                    "Password", style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                )
                Spacer(Modifier.height(10.dp))
                CustomTextField(
                    value = loginViewModel.password,
                    onValueChange = {
                        loginViewModel.onEvent(UserLoginEvents.onPasswordChange(it))
                    },
                    hint = "Enter your password",
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    borderColor = Color.Transparent,
                    cornerRadius = 12,
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = Icons.Outlined.Lock,
                    contentDescription = "Password",
                    isError = loginViewModel.passwordError !=""
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    "Forgot Password?",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(Modifier.height(20.dp))
                Button(onClick = {
                    loginViewModel.onEvent(UserLoginEvents.onLoginClick)

                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Sign In")
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "Or using other method", style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Sign In with Google", color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Sign In with Facebook", color = MaterialTheme.colorScheme.primary)
                }


            }

        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    textStyle: TextStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black),
    backgroundColor: Color = Color.LightGray,
    borderColor: Color = Color.DarkGray,
    cornerRadius: Int = 8,
    visualTransformation: VisualTransformation,
    leadingIcon: ImageVector,
    contentDescription: String,
    isError: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(cornerRadius.dp))
            .border(
                width = 1.dp,
                color = if(isError) Color.Red else if (isFocused) MaterialTheme.colorScheme.primary else borderColor, // Example: change border on focus
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .onFocusChanged { focusState -> // You'll need to implement onFocusChanged or use a similar mechanism
                isFocused = focusState.isFocused
            },
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary), // Customize cursor color
        visualTransformation = visualTransformation, // Or provide your own
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Row {
                    Icon(leadingIcon, contentDescription = contentDescription, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier.width(10.dp))
                    if (value.isEmpty() && hint.isNotEmpty()) {
                        Text(
                            text = hint,
                            style = textStyle.copy(color = Color.Gray),
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UserLoginScreenPrev() {
    JetpackShoppingTheme {
        UserLoginScreenUiForPreview()
    }

}

@Composable
fun AuthTab() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(25.dp))
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Button(
            onClick = {}, colors = ButtonColors(
                containerColor = Color(0XFF31488E),
                contentColor = Color(0XFFFFFFFF),
                disabledContainerColor = Color(0XFFFFFFFF),
                disabledContentColor = Color(0XFFFFFFFF),
            ), contentPadding = PaddingValues(50.dp, 10.dp)
        ) {
            Text("Login", style = TextStyle(fontSize = 18.sp))
        }

        Button(
            onClick = {}, colors = ButtonColors(
                containerColor = Color(0XFF31488E),
                contentColor = Color(0XFFFFFFFF),
                disabledContainerColor = Color(0XFFFFFFFF),
                disabledContentColor = Color(0XFFFFFFFF),
            ), contentPadding = PaddingValues(50.dp, 10.dp)
        ) {
            Text("Register", style = TextStyle(fontSize = 18.sp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthTabPrev() {
    //AuthTab()
}

@Composable
fun LinkText(text: String, onClick: (num: Int) -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(text)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            ),
            start = 0,
            end = text.length
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "",
            start = 0,
            end = text.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun LinkTextPreview() {
    //LinkText(text = "Visit our website") {}
}