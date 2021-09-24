package com.wasilyk.app.mydictionary.model.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getFavoriteDao(): FavoriteDao
}