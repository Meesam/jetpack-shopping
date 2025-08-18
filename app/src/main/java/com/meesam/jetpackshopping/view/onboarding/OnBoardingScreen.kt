package com.meesam.jetpackshopping.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meesam.jetpackshopping.R

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier,onNavigateToLogin: () -> Unit, onNavigateToRegister: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 30.dp, end = 30.dp, top = 60.dp , bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_image),
            contentDescription = "Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Column(modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Various Collection Of the Latest Products", style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))
            Text(
                "Contrary to popular belief, Lorem Ipsum is not simply random text.", style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.LightGray
                ),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))

            Button(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Create Account")
            }
            Spacer(Modifier.height(15.dp))

            Text("Already Have an Account", modifier= Modifier.clickable{
                onNavigateToLogin()
            } ,style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            ))
        }

    }


}

private fun test1(){
}

private fun test2(){}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPrev() {
    OnBoardingScreen(onNavigateToLogin = { test1() }, onNavigateToRegister = { test2() })
}