package com.wasilyk.app.mydictionary.model.datasource.room.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    fun delete(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    suspend fun selectAll(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_table WHERE title LIKE :title")
    fun selectByTitle(title: String): FavoriteEntity?
}