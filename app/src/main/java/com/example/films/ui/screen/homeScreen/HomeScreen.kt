@file:OptIn(ExperimentalMaterialApi::class)

package com.example.films.ui.screen.homeScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.films.data.network.model.Advertising
import com.example.films.data.network.model.User
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.secondaryBackground
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.Image
import com.example.films.ui.view.TextFieldSearch
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    var searchText by remember { mutableStateOf("") }

    var advertising by remember { mutableStateOf<Advertising?>(null) }
    var user by remember { mutableStateOf<User?>(null) }

    val movies = viewModel.getMovies(
        query = searchText.ifEmpty { null }
    ).collectAsLazyPagingItems()

    val lazyListState = rememberLazyListState()

    viewModel.responseUser.onEach { result ->
        user = result
    }.launchWhenStarted()

    viewModel.responseAdvertising.onEach { result ->
        advertising = result
    }.launchWhenStarted()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getUser()
        viewModel.getAdvertising()
    })

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

                itemsIndexed(movies){ index,item ->
                    item ?: return@itemsIndexed

                    if ((index % 10) == 0 && user?.subscription == false){
                        if (advertising?.imageUrl == null){
                            Card(
                                modifier = Modifier.padding(5.dp),
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                backgroundColor = secondaryBackground,
                                onClick = {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(advertising?.webUrl)
                                        )
                                    )
                                }
                            ) {
                                Text(
                                    text = advertising?.title ?: "",
                                    modifier = Modifier.padding(10.dp),
                                    color = primaryText()
                                )
                            }
                        }else {
                            Image(
                                url = advertising?.imageUrl,
                                progressIndicator = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 150.dp)
                                    .padding(5.dp)
                                    .clickable {
                                        context.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(advertising?.webUrl)
                                            )
                                        )
                                    }
                            )
                        }
                    }

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