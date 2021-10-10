package com.wasilyk.app.room_api

import com.wasilyk.app.room_api.favorite.FavoriteDao
import com.wasilyk.app.room_api.history.HistoryDao

interface DatabaseContract {
    fun getHistoryDao(): HistoryDao
    fun getFavoriteDao(): FavoriteDao
}