package com.wasilyk.app.mydictionary.di

import com.wasilyk.app.favorite.di.FavoriteComponent
import com.wasilyk.app.favorite.di.FavoriteModule
import com.wasilyk.app.history.di.HistoryComponent
import com.wasilyk.app.history.di.HistoryModule
import com.wasilyk.app.main.di.MainComponent
import com.wasilyk.app.room_impl.DatabaseComponent
import com.wasilyk.app.room_impl.DatabaseModule
import dagger.Module

@Module(
    subcomponents = [
        DatabaseComponent::class,
        MainComponent::class,
        FavoriteComponent::class,
        HistoryComponent::class
    ],
    includes = [
        DatabaseModule::class,
        FavoriteModule::class,
        HistoryModule::class,
        DatabaseModule::class
    ]
)
interface AppModule