package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import retrofit2.Call

class MainInteractor constructor(private val dataSource: DataSource) {

    fun getDataAsync(word: String, isOnline: Boolean): Call<List<WordDefinition>> =
        dataSource.getListWordDefinitionAsync(word)

    fun getPexelResponse(auth: String, word: String): Call<PexelResponse> =
        dataSource.getPexelResponse(auth, word)
}