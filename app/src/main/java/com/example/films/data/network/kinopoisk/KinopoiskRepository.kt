package com.example.films.data.network.kinopoisk

import com.example.films.data.network.model.MovieInfo
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import javax.inject.Inject

class KinopoiskRepository @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
):BaseApiResponse() {


    suspend fun getMovieInfo(
        kinopoiskId:Int
    ): Result<MovieInfo> = safeApiCall { kinopoiskApi.getMovieInfo(kinopoiskId = kinopoiskId) }
}