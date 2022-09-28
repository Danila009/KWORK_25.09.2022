package com.example.films.di

import com.example.films.data.network.kinopoisk.KinopoiskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class KinopoiskModule {

    @[Provides Singleton]
    fun providerKinopoiskApi(
        @KinopoiskRetrofit retrofit: Retrofit
    ): KinopoiskApi = retrofit.create()
}