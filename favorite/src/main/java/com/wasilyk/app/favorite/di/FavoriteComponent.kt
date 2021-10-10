package com.wasilyk.app.favorite.di

import com.wasilyk.app.favorite.FavoriteFragment
import dagger.Subcomponent

@FavoriteScope
@Subcomponent(
    modules = [FavoriteModule::class]
)
interface FavoriteComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FavoriteComponent
    }

    fun inject(favoriteFragment: FavoriteFragment)
}