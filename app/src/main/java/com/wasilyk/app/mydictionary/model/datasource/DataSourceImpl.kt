package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.retrofit.RetrofitApi
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val retrofitApi: RetrofitApi) :
    DataSource {

    override fun getListWordDefinition(word: String): Single<List<WordDefinition>> =
        retrofitApi.getListWordDefinition(word)
}