package com.example.films.di

import com.example.films.data.network.subscription.SubscriptionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SubscriptionModule {

    @[Provides Singleton]
    fun providerSubscriptionApi(
        @MainRetrofit retrofit: Retrofit
    ):SubscriptionApi = retrofit.create()
}