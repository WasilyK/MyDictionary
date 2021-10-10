package com.wasilyk.app.room_api.favorite

import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    fun delete(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun selectAll(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_table WHERE title LIKE :title")
    fun selectByTitle(title: String): FavoriteEntity?
}