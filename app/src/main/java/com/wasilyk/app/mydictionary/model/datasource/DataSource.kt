package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteEntity
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.dictionary.DictionaryResponse
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import retrofit2.Call

interface DataSource {

    fun getDictionaryResponse(word: String): Call<List<DictionaryResponse>>
    fun getPexelResponse(auth: String, word: String): Call<PexelResponse>

    suspend fun selectAllFromHistory(): List<HistoryEntity>
    fun insertToHistory(historyEntity: HistoryEntity)
    fun deleteHistory(historyEntity: HistoryEntity)
    fun clearHistory()

    suspend fun selectAllFromFavorite(): List<FavoriteEntity>
    fun insertToFavorite(favoriteEntity: FavoriteEntity)
    fun deleteFavorite(favoriteEntity: FavoriteEntity)
    fun selectFavoriteByTitle(title: String): FavoriteEntity?
}