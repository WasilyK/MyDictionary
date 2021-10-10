package com.wasilyk.app.main

import com.wasilyk.app.core.entities.dictionary.DictionaryResponse
import com.wasilyk.app.core.entities.pexels.PexelResponse
import com.wasilyk.app.main.di.MainScope
import retrofit2.Call
import javax.inject.Inject

@MainScope
class MainInteractor @Inject constructor(private val mainDataSource: MainDataSource) {

    fun getDictionaryResponse(word: String, isOnline: Boolean): Call<List<DictionaryResponse>> =
        mainDataSource.getDictionaryResponse(word)

    fun getPexelResponse(auth: String, word: String): Call<PexelResponse> =
        mainDataSource.getPexelResponse(auth, word)
}