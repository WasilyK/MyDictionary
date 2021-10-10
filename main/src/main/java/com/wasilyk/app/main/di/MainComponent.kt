package com.wasilyk.app.main.di

import com.wasilyk.app.main.MainFragment
import com.wasilyk.app.main.di.modules.DictionaryModule
import com.wasilyk.app.main.di.modules.MainModule
import com.wasilyk.app.main.di.modules.PexelsModule
import dagger.Subcomponent

@MainScope
@Subcomponent(
    modules = [
        MainModule::class,
        DictionaryModule::class,
        PexelsModule::class
    ]
)
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainFragment: MainFragment)
}