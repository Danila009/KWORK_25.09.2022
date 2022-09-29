package com.example.films.data.network.user

import com.example.films.data.network.model.*
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
):BaseApiResponse() {

    suspend fun getUser():Result<User> = safeApiCall { userApi.getUser() }

    suspend fun patchSubscription(subscription:Boolean) = userApi.patchSubscription(subscription)

    suspend fun postAdmin(userId:Int) = userApi.postAdmin(userId)

    suspend fun getUsers():Result<List<User>> = safeApiCall { userApi.getUsers() }

    suspend fun authorization(
        body:AuthorizationBody
    ):Result<AuthorizationResponse> = safeApiCall { userApi.authorization(body) }

    suspend fun registration(
        body:RegistrationBody
    ):Result<RegistrationResponse> = safeApiCall { userApi.registration(body) }
}