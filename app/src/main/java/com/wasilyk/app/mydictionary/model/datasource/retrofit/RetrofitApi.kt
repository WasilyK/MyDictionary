package com.wasilyk.app.mydictionary.model.datasource.retrofit

import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {
    @GET("api/v2/entries/en/{word}")
    fun getListWordDefinition(@Path("word") word: String): Single<List<WordDefinition>>
}