package com.wasilyk.app.mydictionary.model.datasource.retrofit.pexel

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PexelsApiFactory {
    private val baseUrl = "https://api.pexels.com/"

    fun createPexelApi(): PexelsApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(PexelsApi::class.java)
}