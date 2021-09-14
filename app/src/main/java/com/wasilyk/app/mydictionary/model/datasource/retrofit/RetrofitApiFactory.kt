package com.wasilyk.app.mydictionary.model.datasource.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApiFactory {

    private var retrofitApi: RetrofitApi? = null
    private val baseUrl = "https://api.dictionaryapi.dev/"

    fun create(): RetrofitApi {
        if(retrofitApi == null) {
            retrofitApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(RetrofitApi::class.java)
        }
        return retrofitApi!!
    }
}