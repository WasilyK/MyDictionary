package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import io.reactivex.rxjava3.core.Single

interface DataSource {
    fun getListWordDefinition(word: String): Single<List<WordDefinition>>
}