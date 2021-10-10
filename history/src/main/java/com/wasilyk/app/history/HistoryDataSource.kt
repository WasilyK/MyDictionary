package com.wasilyk.app.history

import com.wasilyk.app.room_api.history.HistoryEntity

interface HistoryDataSource {

    fun selectAllFromHistory(): List<HistoryEntity>
    fun insertToHistory(historyEntity: HistoryEntity)
    fun deleteHistory(historyEntity: HistoryEntity)
    fun clearHistory()

}