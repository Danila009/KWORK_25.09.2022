package com.example.films.di

import com.example.films.data.network.freekassa.FreekassaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FreekassaModule {

    @[Provides Singleton]
    fun providerFreekassaApi(
        @MainRetrofit retrofit: Retrofit
    ):FreekassaApi = retrofit.create()
}