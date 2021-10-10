package com.wasilyk.app.favorite.di

import com.wasilyk.app.favorite.FavoriteDataSource
import com.wasilyk.app.favorite.FavoriteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface FavoriteModule {

    @Binds
    fun bindFavoriteDataSource(favoriteDataSourceImpl: FavoriteDataSourceImpl):
            FavoriteDataSource
}