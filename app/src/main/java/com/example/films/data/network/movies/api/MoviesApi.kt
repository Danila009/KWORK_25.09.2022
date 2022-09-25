package com.example.films.data.network.movies.api

import com.example.films.data.network.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/api/movies")
    suspend fun getMovies(
        @Query("page") page:Int,
        @Query("limit") pageSize:Int
    ):Movie
}