package com.wasilyk.app.favorite

import com.wasilyk.app.favorite.di.FavoriteScope
import com.wasilyk.app.room_api.favorite.FavoriteDao
import com.wasilyk.app.room_api.favorite.FavoriteEntity
import javax.inject.Inject

class FavoriteDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteDataSource {

    override fun selectAllFromFavorite(): List<FavoriteEntity> =
        favoriteDao.selectAll()

    override fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.delete(favoriteEntity)
    }

    override fun selectFavoriteByTitle(title: String): FavoriteEntity? =
        favoriteDao.selectByTitle(title)

    override fun insertToFavorite(favoriteEntity: FavoriteEntity) =
        favoriteDao.insert(favoriteEntity)
}