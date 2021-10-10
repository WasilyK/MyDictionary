package com.wasilyk.app.room_api.history

import androidx.room.*

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history_table")
    fun selectAll(): List<HistoryEntity>

    @Delete
    fun delete(historyEntity: HistoryEntity)

    @Query("DELETE FROM history_table")
    fun deleteAll()
}
