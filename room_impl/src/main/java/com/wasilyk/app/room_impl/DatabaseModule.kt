package com.wasilyk.app.room_impl

import android.content.Context
import androidx.room.Room
import com.wasilyk.app.room_api.DatabaseContract
import com.wasilyk.app.room_api.favorite.FavoriteDao
import com.wasilyk.app.room_api.history.HistoryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        const val DB_NAME = "dictionary_db"
    }

    @Provides
    fun provideFavoriteDao(databaseContract: DatabaseContract): FavoriteDao =
        databaseContract.getFavoriteDao()

    @Provides
    fun provideHistoryDao(databaseContract: DatabaseContract): HistoryDao =
        databaseContract.getHistoryDao()

    @Provides
    fun provideDatabase(context: Context): DatabaseContract =
        Room.databaseBuilder(
            context,
            DictionaryDatabase::class.java,
            DB_NAME
        ).build()
}