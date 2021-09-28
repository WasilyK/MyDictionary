package com.wasilyk.app.mydictionary.di

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.wasilyk.app.favorite.di.FavoriteComponent
import com.wasilyk.app.history.di.HistoryComponent
import com.wasilyk.app.main.di.MainComponent
import com.wasilyk.app.mydictionary.MainActivity
import com.wasilyk.app.room_impl.DatabaseComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance router: Router,
            @BindsInstance navigatorHolder: NavigatorHolder
        ): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun favoriteComponent(): FavoriteComponent.Factory
    fun historyComponent(): HistoryComponent.Factory
    fun databaseComponent(): DatabaseComponent.Factory

    fun inject(mainActivity: MainActivity)
}