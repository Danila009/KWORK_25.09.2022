package com.example.films.data.network.freekassa

import com.example.films.data.network.model.FreekassaData
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import javax.inject.Inject

class FreekassaRepository @Inject constructor(
    private val freekassaApi: FreekassaApi
):BaseApiResponse() {

    suspend fun getFreekassaByShopId(shopId:Int):Result<FreekassaData> = safeApiCall {
        freekassaApi.getFreekassaByShopId(shopId = shopId)
    }
}