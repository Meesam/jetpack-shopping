package com.meesam.jetpackshopping.view.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.R
import com.meesam.jetpackshopping.model.Product
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.viewmodel.ProductsViewModel

@Composable
fun ProductScreen(onProductClick: (String) -> Unit) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val products by productsViewModel._products.collectAsState()

    when (val result = products) {
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
            if (result.data.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No Category available")
                }
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(result.data) { product ->
                            ProductItem(Modifier.padding(end = 10.dp, bottom = 10.dp), product) {
                                onProductClick(product?.id ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(modifier: Modifier, item: Product?, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .clickable{onClick(item?.id ?: "")}
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.bg_shopping_image),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .height(100.dp)
            )
            Text(item?.title ?: "", style = TextStyle(fontWeight = FontWeight.Light, fontSize = 12.sp))
            Text(
                "$" + item?.price.toString(),
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 10.sp)
            )
        }

        Icon(
            Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.TopEnd),
            tint = Color.Red
        )


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "4.2",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(12.dp),
                    tint = Color.Yellow
                )
            }
        }
    }
}

