package com.wasilyk.app.room_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wasilyk.app.room_api.DatabaseContract
import com.wasilyk.app.room_api.favorite.FavoriteEntity
import com.wasilyk.app.room_api.history.HistoryEntity

@Database(entities = [HistoryEntity::class, FavoriteEntity::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase(), DatabaseContract