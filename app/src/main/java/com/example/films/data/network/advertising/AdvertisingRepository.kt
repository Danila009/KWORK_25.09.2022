package com.example.films.data.network.advertising

import com.example.films.data.network.model.Advertising
import com.example.films.data.network.model.AdvertisingCreate
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import javax.inject.Inject

class AdvertisingRepository @Inject constructor(
    private val advertisingApi: AdvertisingApi
):BaseApiResponse() {

    suspend fun getAdvertisingRandom():Advertising = advertisingApi.getAdvertisingRandom()

    suspend fun postAdvertising(
        body: AdvertisingCreate
    ):Result<Unit?> = safeApiCall { advertisingApi.postAdvertising(body) }

    suspend fun deleteAdvertising(id:Int):Result<Unit?> = safeApiCall { advertisingApi.deleteAdvertising(id) }
}