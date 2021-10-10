package com.wasilyk.app.favorite

import com.wasilyk.app.favorite.di.FavoriteScope
import com.wasilyk.app.room_api.favorite.FavoriteEntity
import javax.inject.Inject

@FavoriteScope
class FavoriteInteractor @Inject constructor(private val favoriteDataSource: FavoriteDataSource) {
    fun selectAllFavorites(): List<FavoriteEntity> = favoriteDataSource.selectAllFromFavorite()
    fun delete(favoriteEntity: FavoriteEntity) =
        favoriteDataSource.deleteFavorite(favoriteEntity)
    fun selectByTitle(title: String): FavoriteEntity? =
        favoriteDataSource.selectFavoriteByTitle(title)
    fun insert(favoriteEntity: FavoriteEntity) =
        favoriteDataSource.insertToFavorite(favoriteEntity)
}