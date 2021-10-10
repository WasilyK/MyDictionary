package com.wasilyk.app.favorite

import com.wasilyk.app.room_api.favorite.FavoriteEntity

interface FavoriteDataSource {

    fun selectAllFromFavorite(): List<FavoriteEntity>
    fun deleteFavorite(favoriteEntity: FavoriteEntity)
    fun selectFavoriteByTitle(title: String): FavoriteEntity?
    fun insertToFavorite(favoriteEntity: FavoriteEntity)
}