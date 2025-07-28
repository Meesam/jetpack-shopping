package com.meesam.jetpackshopping.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.events.UserProfileEvent
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel(), onSignOut: () -> Unit) {

    val userProfile by profileViewModel.userProfile.collectAsState()
    val editProfileSheet by profileViewModel.showEditProfileBottomSheet.collectAsState()

    when (val result = userProfile) {
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
            if (result.data != null) {
                ProfileUi(
                    result.data.name,
                    result.data.email,
                    onClick = onSignOut,
                    onEditProfileClick = {
                        profileViewModel.onEvent(UserProfileEvent.onEditClick)
                    },
                    onDismissBottomSheet = {
                        profileViewModel.onEvent(UserProfileEvent.onDismissSheet)
                    },
                    showBottomSheet = editProfileSheet
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUi(
    name: String,
    email: String,
    onClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onDismissBottomSheet: () -> Unit,
    showBottomSheet: Boolean
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                // here will be image and text
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.DarkGray, shape = CircleShape)
                ) {
                    Text(
                        "M",
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 30.sp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Text(
                        name,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(email)
                }

                Spacer(modifier = Modifier.width(20.dp))
                IconButton(onEditProfileClick) {
                    Icon(Icons.Default.Edit, "Edit Profile")
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier.fillMaxHeight(),
                        sheetState = sheetState,
                        onDismissRequest = onDismissBottomSheet
                    ) {
                        Text(
                            "Swipe up to open sheet. Swipe down to dismiss.",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(10.dp))
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Account Information")
                Spacer(modifier = Modifier.width(10.dp))
                Text("Your Profile")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Open")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(10.dp))
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Account Information")
                Spacer(modifier = Modifier.width(10.dp))
                Text("Your Orders")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Open")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(10.dp))
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Menu, contentDescription = "Account Information")
                Spacer(modifier = Modifier.width(10.dp))
                Text("Your Wishlist")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Open")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onClick, modifier = Modifier.fillMaxWidth(), colors = ButtonColors(
                    containerColor = Color.Red, contentColor = Color.White,
                    disabledContainerColor = Color.Red,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Sign Out")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePrev() {
    ProfileUi(
        "Meesam", "meesam.engineer@gmail.com", onEditProfileClick = {

        },
        onDismissBottomSheet = {

        },
        showBottomSheet = false, onClick = {})
}