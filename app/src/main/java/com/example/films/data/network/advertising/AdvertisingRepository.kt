package com.example.films.data.network.advertising

import com.example.films.data.network.model.Advertising
import com.example.films.data.network.model.AdvertisingCreate
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AdvertisingRepository @Inject constructor(
    private val advertisingApi: AdvertisingApi
):BaseApiResponse() {

    suspend fun getAdvertising():Result<List<Advertising>> = safeApiCall { advertisingApi.getAdvertising() }

    suspend fun getAdvertisingRandom():Advertising = advertisingApi.getAdvertisingRandom()

    suspend fun postAdvertising(
        body: AdvertisingCreate
    ):Result<Advertising> = safeApiCall { advertisingApi.postAdvertising(body) }

    suspend fun deleteAdvertising(id:Int):Result<Unit?> = safeApiCall { advertisingApi.deleteAdvertising(id) }

    suspend fun uploadAdvertisingImage(
        id:Int,
        image:ByteArray
    ):Boolean {
        return try {
            val requestFile = RequestBody.create(
                "application/octet-stream".toMediaTypeOrNull(),
                image
            )

            advertisingApi.uploadAdvertisingImage(
                id = id,
                file = MultipartBody.Part
                    .createFormData("file","file",requestFile)
            )
            true
        }catch (e:IOException){
            e.printStackTrace()
            false
        }catch (e:HttpException){
            e.printStackTrace()
            false
        }
    }
}