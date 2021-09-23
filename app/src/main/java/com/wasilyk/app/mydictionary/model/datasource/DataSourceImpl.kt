package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.retrofit.RetrofitApi
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import retrofit2.Call

class DataSourceImpl(private val retrofitApi: RetrofitApi) :
    DataSource {

    override fun getListWordDefinitionAsync(word: String): Call<List<WordDefinition>> =
        retrofitApi.getListWordDefinitionAsync(word)
}