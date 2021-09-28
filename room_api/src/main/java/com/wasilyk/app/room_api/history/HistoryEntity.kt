package com.wasilyk.app.room_api.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history_table",
    indices = [ Index(value = ["word"], unique = true) ]
)
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "definition")
    val definition: String
)