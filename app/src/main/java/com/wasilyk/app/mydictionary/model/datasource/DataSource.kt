package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteEntity
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import retrofit2.Call

interface DataSource {
    fun getListWordDefinitionAsync(word: String): Call<List<WordDefinition>>
    suspend fun getHistory(): List<HistoryEntity>
    fun deleteHistory(historyEntity: HistoryEntity)
    fun clearHistory()
    fun getPexelResponse(auth: String, word: String): Call<PexelResponse>
    suspend fun getFavoriteEntities(): List<FavoriteEntity>
    fun favoriteItemDelete(favoriteEntity: FavoriteEntity)
}