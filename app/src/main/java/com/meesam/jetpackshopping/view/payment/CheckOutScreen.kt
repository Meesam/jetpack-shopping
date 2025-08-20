package com.meesam.jetpackshopping.view.payment


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meesam.jetpackshopping.navigation.AppDestinations
import com.meesam.jetpackshopping.ui.theme.JetpackShoppingTheme

@Composable
fun CheckOutScreen(mainNavController: NavController, onBack: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("CheckOut Screen")
        Button(onClick = {
            mainNavController.navigate(AppDestinations.ADD_NEW_CARD_ROUTE)
        }) {
            Text("Add New Card")
        }
        Button(onClick = onBack) {
            Text("back")
        }
    }
}

@Composable
fun CheckOutUi(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Address")
                Text("Edit")
            }
            Row {
                Text("Map")
                Text("House Address")
            }
        }
        Spacer(Modifier.height(20.dp))
        Column {
            Text("Products(3)")
            Text("Product List")
        }
        Spacer(Modifier.height(20.dp))
        Column {
          Text("Payment Method")
        }
        Spacer(Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total Amount")
            Text("$32.34")
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Checkout Now")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CheckOutUiPreview(modifier: Modifier = Modifier) {
    JetpackShoppingTheme {
        CheckOutUi()
    }
}

