package com.wasilyk.app.main

import com.wasilyk.app.core.entities.dictionary.DictionaryResponse
import com.wasilyk.app.core.entities.pexels.PexelResponse
import retrofit2.Call

interface MainDataSource {
    fun getDictionaryResponse(word: String): Call<List<DictionaryResponse>>
    fun getPexelResponse(auth: String, word: String): Call<PexelResponse>
}