package com.meesam.jetpackshopping.view.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meesam.jetpackshopping.events.FeedEvents
import com.meesam.jetpackshopping.states.AppState
import com.meesam.jetpackshopping.viewmodel.FeedViewModel

@Composable
fun FeedScreen() {
    val feedViewModel: FeedViewModel = hiltViewModel()
    val categories by feedViewModel.categories.collectAsState()

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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                                       if(it.isSelected) Color.DarkGray else Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(10.dp)
                                    .clickable{
                                        feedViewModel.onEvent(FeedEvents.onCategorySelect(it.name))
                                    }
                            ) {
                                Text(it.name, color = if(it.isSelected) Color.White else Color.Black, style = TextStyle(fontWeight = FontWeight.Medium))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }
        }
    }


}