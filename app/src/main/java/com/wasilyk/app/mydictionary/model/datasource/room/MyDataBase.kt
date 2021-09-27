package com.wasilyk.app.mydictionary.model.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteDao
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteEntity
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryDao
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity

@Database(entities = [HistoryEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getFavoriteDao(): FavoriteDao
}