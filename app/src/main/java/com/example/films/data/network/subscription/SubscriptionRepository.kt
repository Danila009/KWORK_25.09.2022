package com.example.films.data.network.subscription

import com.example.films.data.network.subscription.model.Subscription
import com.example.films.data.network.utils.BaseApiResponse
import com.example.films.data.network.utils.Result
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val subscriptionApi: SubscriptionApi
):BaseApiResponse() {

    suspend fun getSubscriptionMain():Result<Subscription> = safeApiCall {
        subscriptionApi.getSubscriptionMain()
    }
}