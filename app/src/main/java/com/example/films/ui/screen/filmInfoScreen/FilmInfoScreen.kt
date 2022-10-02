package com.example.films.ui.screen.filmInfoScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.films.data.network.model.MovieInfo
import com.example.films.data.network.model.MovieItem
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.screen.filmInfoScreen.view.MovieMediaAlertDialog
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.Image
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach


@SuppressLint("FlowOperatorInvokedInComposition", "ObsoleteSdkInt")
@Composable
fun FilmInfoScreen(
    navController: NavController,
    viewModel: FilmInfoViewModel = hiltViewModel(),
    id: Int,
    kinopoiskId: String,
    imdbId: String,
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    var moviesInfoResult by remember { mutableStateOf<Result<MovieInfo>>(Result.Loading()) }
    var movie by remember { mutableStateOf<MovieItem?>(null) }

    var movieMediaAlertDialog by remember { mutableStateOf(false) }

    viewModel.responseMovieInfo.onEach { result ->
        moviesInfoResult = result
    }.launchWhenStarted()

    viewModel.responseMovie.onEach { result ->
        movie = result
    }.launchWhenStarted()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getMovieInfoById(kinopoiskId = kinopoiskId.toInt())
        viewModel.getMovieById(
            imdbId = imdbId,
            kinopoiskId = kinopoiskId
        )
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        if (movieMediaAlertDialog){
            movie?.let {
                MovieMediaAlertDialog(
                    media = movie!!.media,
                    onDismissRequest = {
                        movieMediaAlertDialog = false
                    },
                    onClickTranslation = {
                        movieMediaAlertDialog = false
                        navController.navigate(
                            Screen.WatchMovieScreen.arguments(
                                url = "https:" + it.path
                            )
                        )
                    }
                )
            }
        }

        LazyColumn {
            if (moviesInfoResult is Result.Error && movie != null){
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = (movie?.ru_title ?: "") +
                                        ", " + (movie?.kinopoisk_id ?: "") +
                                        ", " + (movie?.year ?: ""),
                                modifier = Modifier.padding(start = 10.dp,top = 10.dp),
                                fontWeight = FontWeight.W900,
                                color = primaryText()
                            )

                            Text(
                                text = movie?.orig_title ?: "",
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                                fontWeight = FontWeight.W100,
                                color = primaryText()
                            )
                        }

                        Button(
                            modifier = Modifier.padding(10.dp),
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = primaryBackground
                            ),
                            onClick = {
                                movieMediaAlertDialog = true
                            }
                        ) {
                            Text(
                                text = "Смотреть",
                                color = primaryText()
                            )
                        }
                    }
                }
            }else {
                when(moviesInfoResult){
                    is Result.Error -> item {
                        Text(
                            text = "Error: ${moviesInfoResult.message}",
                            color = Color.Red,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    is Result.Loading -> item {
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
                    is Result.Success -> {
                        item {
                            val moviesInfo = moviesInfoResult.data ?: return@item

                            Image(
                                url = moviesInfo.coverUrl ?: moviesInfo.logoUrl,
                                progressIndicator = false,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .heightIn(max = 400.dp)
                                    .padding(
                                        WindowInsets.systemBars
                                            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                                            .asPaddingValues()
                                    )
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = moviesInfo.nameRu +
                                                ", " + moviesInfo.ratingKinopoisk.toString() +
                                                ", " + moviesInfo.year.toString(),
                                        modifier = Modifier.padding(start = 10.dp,top = 10.dp),
                                        fontWeight = FontWeight.W900,
                                        color = primaryText()
                                    )

                                    Text(
                                        text = moviesInfo.nameEn ?: moviesInfo.nameOriginal ?: "",
                                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                                        fontWeight = FontWeight.W100,
                                        color = primaryText()
                                    )
                                }

                                movie?.let {
                                    Button(
                                        modifier = Modifier.padding(10.dp),
                                        shape = AbsoluteRoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = primaryBackground
                                        ),
                                        onClick = {
                                            movieMediaAlertDialog = true
                                        }
                                    ) {
                                        Text(text = "Смотреть",
                                            color = primaryText())
                                    }
                                }
                            }

                            LazyRow {
                                items(moviesInfo.countries){ countrie ->
                                    Text(
                                        text = countrie.country,
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryText()
                                    )
                                }
                            }

                            LazyRow {
                                items(moviesInfo.genres){ genre ->
                                    Text(
                                        text = genre.genre,
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryText()
                                    )
                                }
                            }

                            Text(
                                text = moviesInfo.description ?: moviesInfo.shortDescription,
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.W100,
                                color = primaryText()
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}