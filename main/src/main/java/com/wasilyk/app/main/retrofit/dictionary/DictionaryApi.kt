package com.wasilyk.app.main.retrofit.dictionary

import com.wasilyk.app.core.entities.dictionary.DictionaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    fun getListWordDefinitionAsync(@Path("word") word: String): Call<List<DictionaryResponse>>
}