package com.example.films.data.network.utils

import retrofit2.Response

// Get API response result
abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        try {
            val response = apiCall()
            // request code 200..300
            if (response.isSuccessful) {
                val body = response.body()
                return Result.Success(body)
            }
            return Result.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }
}