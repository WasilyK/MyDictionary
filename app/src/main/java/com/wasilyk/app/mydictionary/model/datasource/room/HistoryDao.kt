package com.wasilyk.app.mydictionary.model.datasource.room

import androidx.room.*

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyEntity: HistoryEntity)

    @Delete
    fun delete(historyEntity: HistoryEntity)

    @Query("DELETE FROM history_table")
    fun deleteAll()

    @Query("SELECT * FROM history_table")
    suspend fun selectAll(): List<HistoryEntity>
}