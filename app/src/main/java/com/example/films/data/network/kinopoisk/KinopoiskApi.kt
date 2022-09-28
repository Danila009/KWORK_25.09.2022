package com.example.films.data.network.kinopoisk

import com.example.films.data.network.model.MovieInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface KinopoiskApi {

    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieInfo(
        @Path("id") kinopoiskId:Int
    ):Response<MovieInfo>
}