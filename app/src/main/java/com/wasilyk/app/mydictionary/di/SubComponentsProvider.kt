package com.wasilyk.app.mydictionary.di

import com.wasilyk.app.favorite.di.FavoriteComponent
import com.wasilyk.app.favorite.di.FavoriteComponentProvider
import com.wasilyk.app.history.di.HistoryComponent
import com.wasilyk.app.history.di.HistoryComponentProvider
import com.wasilyk.app.main.di.MainComponent
import com.wasilyk.app.main.di.MainComponentProvider
import com.wasilyk.app.mydictionary.App
import com.wasilyk.app.room_impl.DatabaseComponent
import com.wasilyk.app.room_impl.DatabaseComponentProvider

interface SubComponentsProvider :
    MainComponentProvider,
    FavoriteComponentProvider,
    HistoryComponentProvider,
    DatabaseComponentProvider {

    override fun provideMainComponent(): MainComponent =
        App.instance.appComponent.mainComponent().create()

    override fun provideFavoriteComponent(): FavoriteComponent =
        App.instance.appComponent.favoriteComponent().create()


    override fun provideHistoryComponent(): HistoryComponent =
        App.instance.appComponent.historyComponent().create()

    override fun provideDatabaseComponent(): DatabaseComponent =
        App.instance.appComponent.databaseComponent().create()
}