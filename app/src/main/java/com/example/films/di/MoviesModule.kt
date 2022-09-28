package com.example.films.di

import com.example.films.data.network.movies.api.MoviesApi
import com.example.films.data.network.movies.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {

    @[Provides Singleton]
    fun providerMoviesApi(
       @MoviesRetrofit retrofit: Retrofit
    ): MoviesApi = retrofit.create()

    @[Provides Singleton]
    fun providerMoviesRepository(
        moviesApi: MoviesApi
    ):MoviesRepository = MoviesRepository(
        moviesApi = moviesApi
    )
}