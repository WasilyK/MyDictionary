package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity

class HistoryInteractor (private val dataSource: DataSource) {
    suspend fun selectAllHistories(): List<HistoryEntity> =
        dataSource.selectAllFromHistory()

    fun delete(historyEntity: HistoryEntity) =
        dataSource.deleteHistory(historyEntity)

    fun clear() =
        dataSource.clearHistory()

    fun insert(historyEntity: HistoryEntity) =
        dataSource.insertToHistory(historyEntity)
}