package com.example.films.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @[Provides Singleton]
    fun providerRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl("https://videocdn.tv")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}