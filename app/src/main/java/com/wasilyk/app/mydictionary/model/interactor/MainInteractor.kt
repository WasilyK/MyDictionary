package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.entities.dictionary.DictionaryResponse
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import retrofit2.Call

class MainInteractor constructor(private val dataSource: DataSource) {

    fun getDictionaryResponse(word: String, isOnline: Boolean): Call<List<DictionaryResponse>> =
        dataSource.getDictionaryResponse(word)

    fun getPexelResponse(auth: String, word: String): Call<PexelResponse> =
        dataSource.getPexelResponse(auth, word)
}