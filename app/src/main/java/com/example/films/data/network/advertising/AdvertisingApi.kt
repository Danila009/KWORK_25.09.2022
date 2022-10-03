package com.example.films.data.network.advertising

import com.example.films.data.network.model.Advertising
import com.example.films.data.network.model.AdvertisingCreate
import com.example.films.data.network.utils.Result
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AdvertisingApi {

    @GET("/filmsvvvzzz/api/Advertising")
    suspend fun getAdvertising():Response<List<Advertising>>

    @GET("/filmsvvvzzz/api/Advertising/Random")
    suspend fun getAdvertisingRandom():Advertising

    @POST("/filmsvvvzzz/api/Advertising")
    suspend fun postAdvertising(
        @Body body:AdvertisingCreate
    ):Response<Advertising>

    @DELETE("/filmsvvvzzz/api/Advertising/{id}")
    suspend fun deleteAdvertising(
        @Path("id") id:Int
    ):Response<Unit?>

    @Multipart
    @POST("/filmsvvvzzz/api/Advertising/{id}/Image")
    suspend fun uploadAdvertisingImage(
        @Path("id") id:Int,
        @Part file:MultipartBody.Part
    )
}