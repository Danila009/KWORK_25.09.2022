package com.example.films.di

import com.example.films.data.network.freekassaOrder.FreekassaOrderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FreekassaOrderModule {

    @[Provides Singleton]
    fun providerFreekassaOrderApi(
        @FreekassaOrderRetrofit retrofit: Retrofit
    ): FreekassaOrderApi = retrofit.create()
}