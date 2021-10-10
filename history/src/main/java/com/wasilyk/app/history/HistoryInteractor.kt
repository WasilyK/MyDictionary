package com.wasilyk.app.history

import com.wasilyk.app.history.di.HistoryScope
import com.wasilyk.app.room_api.history.HistoryEntity
import javax.inject.Inject

@HistoryScope
class HistoryInteractor @Inject constructor(private val historyDataSource: HistoryDataSource) {

    fun selectAllHistories(): List<HistoryEntity> =
        historyDataSource.selectAllFromHistory()

    fun delete(historyEntity: HistoryEntity) =
        historyDataSource.deleteHistory(historyEntity)

    fun clear() =
        historyDataSource.clearHistory()

    fun insert(historyEntity: HistoryEntity) =
        historyDataSource.insertToHistory(historyEntity)
}