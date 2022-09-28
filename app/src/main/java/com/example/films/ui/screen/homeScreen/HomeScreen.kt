package com.example.films.ui.screen.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.OnLifecycleEvent
import com.example.films.ui.view.TextFieldSearch

private var lazyListPosition = 0

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    var searchText by remember { mutableStateOf("") }

    val movies = viewModel.getMovies(
        query = searchText.ifEmpty { null }
    ).collectAsLazyPagingItems()

    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = movies, block = {
        if (
            movies.loadState.refresh !is LoadState.Loading
        ) {
            lazyListState.animateScrollToItem(lazyListPosition)
        }
    })

    OnLifecycleEvent { owner, event ->
        if (event == Lifecycle.Event.ON_PAUSE){
            lazyListPosition = lazyListState.firstVisibleItemIndex
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextFieldSearch(
                    value = searchText,
                    onValueChange = { searchText = it },
                    onClose = {
                        searchText = ""
                        focusManager.clearFocus()
                    }
                )
            }

            LazyColumn(
                state = lazyListState
            ) {

                if (
                    movies.loadState.refresh is LoadState.Loading
                ) {
                    item {
                        Column(
                            modifier = Modifier.size(
                                width = screenWidthDp,
                                height = screenHeightDp
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = tintColor
                            )
                        }
                    }
                }

                items(movies){ item ->
                    item ?: return@items

                    Column(
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.FilmInfoScreen.arguments(
                                id = item.id,
                                kinopoiskId = item.kinopoisk_id,
                                imdbId = item.imdb_id
                            ))
                        }
                    ) {
                        Text(
                            text = item.ru_title,
                            modifier = Modifier.padding(start = 10.dp,top = 10.dp),
                            fontWeight = FontWeight.W900,
                            color = primaryText()
                        )

                        Text(
                            text = item.orig_title,
                            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                            fontWeight = FontWeight.W100,
                            color = primaryText()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = item.year,
                                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
                                fontWeight = FontWeight.W500,
                                color = primaryText()
                            )
                        }

                        Divider(
                            color = primaryText()
                        )
                    }
                }

                if (movies.loadState.append is LoadState.Loading
                    && movies.itemCount > 0
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(15.dp),
                                color = tintColor
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}