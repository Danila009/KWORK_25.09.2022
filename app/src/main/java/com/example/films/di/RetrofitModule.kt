package com.example.films.di

import com.example.films.data.database.user.UserDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class KinopoiskRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FreekassaOrderRetrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @[Provides Singleton FreekassaOrderRetrofit]
    fun providerRetrofitFreekassaOrder(
        gson: Gson
    ):Retrofit = Retrofit.Builder()
        .baseUrl("https://api.freekassa.ru")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @[Provides Singleton MainRetrofit]
    fun providerRetrofitMain(
        @MainRetrofit okttpClient:OkHttpClient,
        gson: Gson
    ):Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000")
        .client(okttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @[Provides Singleton MoviesRetrofit]
    fun providerRetrofit(
        @MoviesRetrofit okttpClient:OkHttpClient,
        gson: Gson
    ):Retrofit = Retrofit.Builder()
        .baseUrl("https://videocdn.tv")
        .client(okttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @[Provides Singleton KinopoiskRetrofit]
    fun providerRetrofitKinopoisk(
        @KinopoiskRetrofit okttpClient: OkHttpClient,
        gson: Gson
    ):Retrofit = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .client(okttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @[Provides Singleton MoviesRetrofit]
    fun providerOkHttpClient():OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
        .writeTimeout(5, TimeUnit.MINUTES) // write timeout
        .readTimeout(5, TimeUnit.MINUTES) // read timeout
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("API_TOKEN","vxqSnPX5wkDTAAMdGNZNKJsegnXpWslg")
                .build()
            it.proceed(request = request)
        }
        .build()

    @[Provides Singleton KinopoiskRetrofit]
    fun providerOkHttpClientKinopoisk():OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("X-API-KEY","ab67ce7d-90cf-4d1b-b354-7474b82c9f38")
                .build()
            it.proceed(request = request)
        }
        .build()

    @[Provides Singleton MainRetrofit]
    fun providerOkHttpClientMain(
        userDataSource: UserDataSource
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization","Bearer ${userDataSource.getToken()}")
                .build()
            it.proceed(request = request)
        }
        .build()

    @[Provides Singleton]
    fun providerGson():Gson = GsonBuilder()
        .setLenient()
        .create()
}