package com.example.films.data.network.freekassa

import com.example.films.data.network.model.FreekassaData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FreekassaApi {
    
    @GET("/filmsvvvzzz/api/Freekassa/ShopId")
    suspend fun getFreekassaByShopId(
        @Query("shopId") shopId:Int
    ):Response<FreekassaData>
}