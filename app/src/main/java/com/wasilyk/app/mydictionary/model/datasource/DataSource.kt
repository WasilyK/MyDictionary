package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import retrofit2.Call

interface DataSource {
    fun getListWordDefinitionAsync(word: String): Call<List<WordDefinition>>
}