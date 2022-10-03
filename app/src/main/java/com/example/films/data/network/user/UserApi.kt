package com.example.films.data.network.user

import com.example.films.data.network.model.*
import com.example.films.data.network.utils.Result
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("/filmsvvvzzz/api/User")
    suspend fun getUser():Response<User>

    @PATCH("/filmsvvvzzz/api/User/Subscription")
    suspend fun patchSubscription(
        @Query("subscription") subscription:Boolean
    )

    @POST("/filmsvvvzzz/api/User/{id}/Admin")
    suspend fun postAdmin(
        @Path("id") userId:Int
    )

    @GET("/filmsvvvzzz/api/Users")
    suspend fun getUsers():Response<List<User>>

    @POST("/filmsvvvzzz/api/User/Authorization")
    suspend fun authorization(
        @Body body: AuthorizationBody
    ):Response<AuthorizationResponse>

    @POST("/filmsvvvzzz/api/User/Registration")
    suspend fun registration(
        @Body body: RegistrationBody
    ):Response<RegistrationResponse>
}