package com.example.films.ui.screen.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.primaryText

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val movies = viewModel.getMovies().collectAsLazyPagingItems()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn {
            items(movies){ item ->
                item ?: return@items
                
                Column {
                    Text(
                        text = item.ru_title,
                        modifier = Modifier.padding(10.dp),
                        fontWeight = FontWeight.W900,
                        color = primaryText
                    )
                    
                    Divider(
                        color = primaryText
                    )
                }
            }
            
            item { 
                Spacer(modifier = Modifier.height(80.dp))
            }
        }   
    }
}