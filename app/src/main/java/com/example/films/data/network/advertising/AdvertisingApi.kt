package com.example.films.data.network.advertising

import com.example.films.data.network.model.Advertising
import com.example.films.data.network.model.AdvertisingCreate
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdvertisingApi {

    @GET("/api/Advertising/Random")
    suspend fun getAdvertisingRandom():Advertising

    @POST("/api/Advertising")
    suspend fun postAdvertising(
        body:AdvertisingCreate
    ):Response<Unit?>

    @DELETE("/api/Advertising/{id}")
    suspend fun deleteAdvertising(
        @Path("id") id:Int
    ):Response<Unit?>
}