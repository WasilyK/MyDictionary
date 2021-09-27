package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.retrofit.dictionary.DictionaryApi
import com.wasilyk.app.mydictionary.model.datasource.retrofit.pexel.PexelsApi
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteDao
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteEntity
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryDao
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.dictionary.DictionaryResponse
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import retrofit2.Call

class DataSourceImpl(
    private val dictionaryApi: DictionaryApi,
    private val historyDao: HistoryDao,
    private val pexelsApi: PexelsApi,
    private val favoriteDao: FavoriteDao
) : DataSource {

    override fun getDictionaryResponse(word: String): Call<List<DictionaryResponse>> =
        dictionaryApi.getListWordDefinitionAsync(word)

    override suspend fun selectAllFromHistory(): List<HistoryEntity> =
        historyDao.selectAll()

    override fun deleteHistory(historyEntity: HistoryEntity) =
        historyDao.delete(historyEntity)

    override fun clearHistory() {
        historyDao.deleteAll()
    }

    override fun getPexelResponse(auth: String, word: String): Call<PexelResponse> =
        pexelsApi.getPexelsResponse(auth, word)

    override suspend fun selectAllFromFavorite(): List<FavoriteEntity> =
        favoriteDao.selectAll()

    override fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.delete(favoriteEntity)
    }

    override fun selectFavoriteByTitle(title: String): FavoriteEntity? =
        favoriteDao.selectByTitle(title)

    override fun insertToFavorite(favoriteEntity: FavoriteEntity) =
        favoriteDao.insert(favoriteEntity)

    override fun insertToHistory(historyEntity: HistoryEntity) =
        historyDao.insert(historyEntity)
}