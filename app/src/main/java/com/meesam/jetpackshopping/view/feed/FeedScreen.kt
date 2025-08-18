package com.meesam.jetpackshopping.view.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.R
import com.meesam.jetpackshopping.events.FeedEvents
import com.meesam.jetpackshopping.model.Product
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.ui.theme.JetpackShoppingTheme
import com.meesam.jetpackshopping.viewmodel.FeedViewModel

@Composable
fun FeedScreen() {
    val feedViewModel: FeedViewModel = hiltViewModel()
    val categories by feedViewModel.categories.collectAsState()
    val products by feedViewModel.products.collectAsState()
    val loading by feedViewModel._isLoading.collectAsState()
    val productHistory by feedViewModel.productsHistory.collectAsState()
    val recommendedProduct by feedViewModel.recommendedProducts.collectAsState()
    val scrollState = rememberScrollState()

    if (loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            when (val result = categories) {
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
                    /*Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }*/
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
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            LazyRow(
                                modifier = Modifier.padding(vertical = 6.dp),
                            ) {
                                items(result.data) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .background(
                                                if (it.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(10.dp)
                                            .clickable {
                                                feedViewModel.onEvent(FeedEvents.onCategorySelect(it.name))
                                            }
                                    ) {
                                        Text(
                                            it.name,
                                            color = if (it.isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                                            style = TextStyle(fontWeight = FontWeight.Medium)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Pager()
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
                    /*Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }*/
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
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Top Products",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                )

                                Text(
                                    "See All",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            LazyRow(
                                modifier = Modifier.padding(vertical = 6.dp),
                            ) {
                                items(result.data) {
                                    ProductItem(it)
                                    Spacer(Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (val result = productHistory) {
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
                    /*Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }*/
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
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Recently bought Products",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                )

                                Text(
                                    "See All",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            LazyRow(
                                modifier = Modifier.padding(vertical = 6.dp),
                            ) {
                                items(result.data) {
                                    ProductItem(it)
                                    Spacer(Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (val result = recommendedProduct) {
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
                    /*Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }*/
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
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Recommended deals for you",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            LazyRow(
                                modifier = Modifier.padding(vertical = 6.dp),
                            ) {
                                items(result.data) {
                                    ProductItem(it)
                                    Spacer(Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductGrid() {
    LazyRow {
        val products = listOf<Product>(
            Product(
                title = "IPhone",
                description = "This is Iphone",
                price = 12.55,
                category = "Mobile"
            ), Product(
                title = "Samsung",
                description = "This is Samsung",
                price = 12.55,
                category = "Mobile"
            ),
            Product(
                title = "Sony",
                description = "This is Sony",
                price = 12.55,
                category = "Mobile"
            ), Product(
                title = "Idea",
                description = "This is Idea",
                price = 12.55,
                category = "Mobile"
            )
        )
        items(products) { item ->
            //Text(item.title)

            ProductItem(item)
        }
    }

}


@Composable
fun ProductItem(item: Product?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .shadow( // Add the shadow modifier here
                    elevation = 10.dp, // Adjust the elevation as needed
                    shape = RoundedCornerShape(20.dp), // Match your existing clip shape
                    clip = false // Set to true if you want the shadow to be clipped by the shape as well
                )
                .background(
                    MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)

        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_shopping_image),
                contentDescription = "Logo",
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier
                    .align(Alignment.TopEnd),
                tint = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                item?.title ?: "",
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                item?.category ?: "",
                style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "$" + item?.price.toString(),
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {

    val data = Product(
        title = "IPhone",
        description = "This is Iphone",
        price = 12.55,
        category = "Mobile"
    )
    JetpackShoppingTheme {
       // ProductItem(data)
    }

}


@Composable
fun Pager() {
    val images = listOf(
        R.drawable.pager_image_1,
        R.drawable.pager_image_2,
        R.drawable.pager_image_3,
        R.drawable.pager_image_4 // Assuming you want to repeat pager_image_2 for the 4th item
    )
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })
    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "pager_image_1",
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PagePreview() {
    JetpackShoppingTheme {
        //ProductGrid()
        Pager()
    }

}