package com.example.films.data.network.movies.repository

import com.example.films.data.network.model.Movie
import com.example.films.data.network.movies.api.MoviesApi
import com.example.films.data.network.utils.BaseApiResponse
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi
):BaseApiResponse() {
    suspend fun getMovies(
        imdbId:String? = null,
        kinopoiskId:String? = null,
        query:String? = null,
        page:Int,
        pageSize:Int
    ):Movie = moviesApi.getMovies(
        query = query,
        imdbId = imdbId,
        kinopoiskId = kinopoiskId,
        page = page,
        pageSize = pageSize
    )
}