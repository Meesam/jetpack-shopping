package com.meesam.jetpackshopping.view.auth

import android.content.res.Resources
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meesam.jetpackshopping.R




@Composable
fun UserLoginScreen() {
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
               value = "",
               onValueChange = {},
               placeholder = {
                   Text("Username")
               },
               shape = RoundedCornerShape(16.dp),
               leadingIcon = {
                   Icon(Icons.Default.Person, contentDescription = "User")
               },
               modifier = Modifier.fillMaxWidth()
           )
           Spacer(modifier = Modifier.height(20.dp))
           OutlinedTextField(
               value = "",
               onValueChange = {},
               placeholder = {
                   Text("Password")
               },
               shape = RoundedCornerShape(16.dp),
               leadingIcon = {
                   Icon(Icons.Default.Lock, contentDescription = "User")
               },
               visualTransformation = PasswordVisualTransformation(),
               modifier = Modifier.fillMaxWidth()
           )
           Spacer(modifier = Modifier.height(20.dp))
           Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
               Button(
                   onClick = { /*TODO*/ },
                   shape = RoundedCornerShape(16.dp),
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(text = "Login", style = TextStyle(fontSize = 20.sp))
               }
           }

           Spacer(modifier = Modifier.height(20.dp))
           Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

               Text(text = "New User?", style = TextStyle(fontSize = 20.sp))

               Text(text = "Forgot Password?", style = TextStyle(fontSize = 20.sp))
           }

       }
   }

}


@Preview(showBackground = true)
@Composable
fun UserLoginScreenPrev() {
    UserLoginScreen()
}