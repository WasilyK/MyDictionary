package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.retrofit.RetrofitApiFactory
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import io.reactivex.rxjava3.core.Single

class DataSourceImpl : DataSource {

    private val retrofitApi = RetrofitApiFactory.create()

    override fun getListWordDefinition(word: String): Single<List<WordDefinition>> =
        retrofitApi.getListWordDefinition(word)
}