package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(private val dataSource: DataSource) {

    fun getData(word: String, isOnline: Boolean): Single<List<WordDefinition>> =
        dataSource.getListWordDefinition(word)
}