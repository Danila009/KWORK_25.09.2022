package com.example.films.data.network.subscription.model

data class Subscription(
    val id:Int,
    val price:Int,
    val mainSubscription:Boolean,
    val shopId:Int
)