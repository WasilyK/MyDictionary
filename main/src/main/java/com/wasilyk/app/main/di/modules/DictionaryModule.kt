package com.wasilyk.app.main.di.modules

import com.google.gson.GsonBuilder
import com.wasilyk.app.main.di.MainScope
import com.wasilyk.app.main.retrofit.dictionary.DictionaryApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DictionaryModule {

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }

    @MainScope
    @Provides
    fun provideRetrofitApi(): DictionaryApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(DictionaryApi::class.java)
}