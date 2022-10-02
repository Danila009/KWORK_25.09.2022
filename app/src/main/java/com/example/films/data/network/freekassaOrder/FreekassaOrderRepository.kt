package com.example.films.data.network.freekassaOrder

import com.example.films.data.network.freekassaOrder.model.FreekassaOrderBody
import com.example.films.data.network.freekassaOrder.model.FreekassaOrderResponse
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import javax.inject.Inject

class FreekassaOrderRepository @Inject constructor(
    private val freekassaOrderApi: FreekassaOrderApi
):BaseApiResponse() {

    suspend fun getOrders(
        body:FreekassaOrderBody
    ):Result<FreekassaOrderResponse> = safeApiCall {
        freekassaOrderApi.getOrders(body = body)
    }
}