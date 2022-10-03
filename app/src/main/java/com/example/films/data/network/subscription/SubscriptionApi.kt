package com.example.films.data.network.subscription

import com.example.films.data.network.subscription.model.Subscription
import retrofit2.Response
import retrofit2.http.GET

interface SubscriptionApi {

    @GET("/filmsvvvzzz/api/Subscription/Main")
    suspend fun getSubscriptionMain():Response<Subscription>
}