package com.example.films.data.network.movies.repository

import com.example.films.data.network.model.Movie
import com.example.films.data.network.movies.api.MoviesApi
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MoviesApi
) {
    suspend fun getMovies(
        page:Int,
        pageSize:Int
    ):Movie = api.getMovies(
        page = page,
        pageSize = pageSize
    )
}