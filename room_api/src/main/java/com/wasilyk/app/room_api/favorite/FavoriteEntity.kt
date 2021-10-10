package com.wasilyk.app.room_api.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_table",
    indices = [ Index(value = ["title"], unique = true) ]
)
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "definition")
    val definition: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?
)