package com.wasilyk.app.mydictionary.model.datasource.retrofit

import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {
    @GET("api/v2/entries/en/{word}")
    fun getListWordDefinitionAsync(@Path("word") word: String): Call<List<WordDefinition>>
}