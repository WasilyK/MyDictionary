package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteEntity

class FavoriteInteractor(private val dataSource: DataSource) {
    suspend fun selectAllFavorites(): List<FavoriteEntity> = dataSource.selectAllFromFavorite()
    fun delete(favoriteEntity: FavoriteEntity) =
        dataSource.deleteFavorite(favoriteEntity)
    fun selectByTitle(title: String): FavoriteEntity? =
        dataSource.selectFavoriteByTitle(title)
    fun insert(favoriteEntity: FavoriteEntity) =
        dataSource.insertToFavorite(favoriteEntity)
}