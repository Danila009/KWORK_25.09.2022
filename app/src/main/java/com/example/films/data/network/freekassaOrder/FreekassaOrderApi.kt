package com.example.films.data.network.freekassaOrder

import com.example.films.data.network.freekassaOrder.model.FreekassaOrderBody
import com.example.films.data.network.freekassaOrder.model.FreekassaOrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FreekassaOrderApi {

    @POST("/v1/orders")
    suspend fun getOrders(
        @Body body: FreekassaOrderBody
    ):Response<FreekassaOrderResponse>
}