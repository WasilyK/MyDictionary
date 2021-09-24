package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryEntity

class HistoryInteractor (private val dataSource: DataSource) {
    suspend fun getHistory(): List<HistoryEntity> =
        dataSource.getHistory()

    fun deleteHistory(historyEntity: HistoryEntity) =
        dataSource.deleteHistory(historyEntity)

    fun clearHistory() =
        dataSource.clearHistory()
}