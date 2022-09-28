package com.example.films.data.network.movies.api

import com.example.films.data.network.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/api/movies")
    suspend fun getMovies(
        @Query("api_token") token:String = "vxqSnPX5wkDTAAMdGNZNKJsegnXpWslg",
        @Query("query") query:String?,
        @Query("imdb_id") imdbId:String?,
        @Query("kinopoisk_id") kinopoiskId:String?,
        @Query("page") page:Int,
        @Query("limit") pageSize:Int
    ):Movie
}