package com.example.films.di

import com.example.films.data.network.user.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserModule {

    @[Provides Singleton]
    fun providerUserApi(
        @MainRetrofit retrofit: Retrofit
    ):UserApi = retrofit.create()

}