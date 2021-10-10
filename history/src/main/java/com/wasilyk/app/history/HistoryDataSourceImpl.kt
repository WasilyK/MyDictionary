package com.wasilyk.app.history

import com.wasilyk.app.history.di.HistoryScope
import com.wasilyk.app.room_api.history.HistoryDao
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(private val historyDao: HistoryDao)
    : HistoryDataSource{

    override fun selectAllFromHistory(): List<com.wasilyk.app.room_api.history.HistoryEntity> =
        historyDao.selectAll()

    override fun deleteHistory(historyEntity: com.wasilyk.app.room_api.history.HistoryEntity) =
        historyDao.delete(historyEntity)

    override fun insertToHistory(historyEntity: com.wasilyk.app.room_api.history.HistoryEntity) =
        historyDao.insert(historyEntity)

    override fun clearHistory() {
        historyDao.deleteAll()
    }
}