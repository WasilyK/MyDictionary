package com.wasilyk.app.mydictionary.model.datasource

import com.wasilyk.app.mydictionary.model.datasource.retrofit.RetrofitApi
import com.wasilyk.app.mydictionary.model.datasource.retrofit.pexel.PexelsApi
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteDao
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteEntity
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryDao
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import retrofit2.Call

class DataSourceImpl(
    private val retrofitApi: RetrofitApi,
    private val historyDao: HistoryDao,
    private val pexelsApi: PexelsApi,
    private val favoriteDao: FavoriteDao
) : DataSource {

    override fun getListWordDefinitionAsync(word: String): Call<List<WordDefinition>> =
        retrofitApi.getListWordDefinitionAsync(word)

    override suspend fun getHistory(): List<HistoryEntity> =
        historyDao.selectAll()

    override fun deleteHistory(historyEntity: HistoryEntity) =
        historyDao.delete(historyEntity)

    override fun clearHistory() {
        historyDao.deleteAll()
    }

    override fun getPexelResponse(auth: String, word: String): Call<PexelResponse> =
        pexelsApi.getPexelsResponse(auth, word)

    override suspend fun getFavoriteEntities(): List<FavoriteEntity> =
        favoriteDao.selectAll()

    override fun favoriteItemDelete(favoriteEntity: FavoriteEntity) {
        favoriteDao.delete(favoriteEntity)
    }
}