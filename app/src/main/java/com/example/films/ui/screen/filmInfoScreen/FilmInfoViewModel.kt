package com.example.films.ui.screen.filmInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.network.kinopoisk.KinopoiskRepository
import com.example.films.data.network.model.Movie
import com.example.films.data.network.model.MovieInfo
import com.example.films.data.network.model.MovieItem
import com.example.films.data.network.movies.repository.MoviesRepository
import com.example.films.data.network.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmInfoViewModel @Inject constructor(
    private val kinopoiskRepository: KinopoiskRepository,
    private val moviesRepository: MoviesRepository
):ViewModel() {

    private val _responseMovieInfo = MutableStateFlow<Result<MovieInfo>>(Result.Loading())
    val responseMovieInfo = _responseMovieInfo.asStateFlow()

    private val _responseMovie = MutableStateFlow<MovieItem?>(null)
    val responseMovie = _responseMovie.filterNotNull()

    fun getMovieInfoById(kinopoiskId:Int){
        viewModelScope.launch {
            try {
                val response = kinopoiskRepository.getMovieInfo(kinopoiskId = kinopoiskId)
                _responseMovieInfo.value = response
            }catch (e:Exception){}
        }
    }

    fun getMovieById(
        imdbId:String,
        kinopoiskId:String,
    ){
        viewModelScope.launch {
            val response = moviesRepository.getMovies(
                imdbId = imdbId,
                kinopoiskId = kinopoiskId,
                page = 1,
                pageSize = 1
            )

            _responseMovie.value = response.data.last()
        }
    }
}