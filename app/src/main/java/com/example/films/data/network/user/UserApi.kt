package com.example.films.data.network.user

import com.example.films.data.network.model.*
import com.example.films.data.network.utils.Result
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("/api/User")
    suspend fun getUser():Response<User>

    @PATCH("/api/User/Subscription")
    suspend fun patchSubscription(subscription:Boolean)

    @POST("/api/User/{id}/Admin")
    suspend fun postAdmin(
        @Path("id") userId:Int
    )

    @GET("/api/Users")
    suspend fun getUsers():Response<List<User>>

    @POST("/api/User/Authorization")
    suspend fun authorization(
        @Body body: AuthorizationBody
    ):Response<AuthorizationResponse>

    @POST("/api/User/Registration")
    suspend fun registration(
        @Body body: RegistrationBody
    ):Response<RegistrationResponse>
}