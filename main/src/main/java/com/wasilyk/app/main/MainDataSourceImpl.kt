package com.wasilyk.app.main

import com.wasilyk.app.core.entities.dictionary.DictionaryResponse
import com.wasilyk.app.core.entities.pexels.PexelResponse
import com.wasilyk.app.main.retrofit.dictionary.DictionaryApi
import com.wasilyk.app.main.retrofit.pexel.PexelsApi
import retrofit2.Call
import javax.inject.Inject

class MainDataSourceImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val pexelsApi: PexelsApi
) : MainDataSource {

    override fun getDictionaryResponse(word: String): Call<List<DictionaryResponse>> =
        dictionaryApi.getListWordDefinitionAsync(word)

    override fun getPexelResponse(auth: String, word: String): Call<PexelResponse> =
        pexelsApi.getPexelsResponse(auth, word)
}