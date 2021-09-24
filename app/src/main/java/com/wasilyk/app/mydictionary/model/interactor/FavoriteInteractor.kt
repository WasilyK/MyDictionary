package com.wasilyk.app.mydictionary.model.interactor

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteEntity

class FavoriteInteractor(private val dataSource: DataSource) {
    suspend fun getData(): List<FavoriteEntity> = dataSource.getFavoriteEntities()
    fun favoriteItemDelete(favoriteEntity: FavoriteEntity) =
        dataSource.favoriteItemDelete(favoriteEntity)
}