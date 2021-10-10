package com.wasilyk.app.mydictionary

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.wasilyk.app.mydictionary.di.AppComponent
import com.wasilyk.app.mydictionary.di.DaggerAppComponent
import com.wasilyk.app.mydictionary.di.SubComponentsProvider

class App : Application(), SubComponentsProvider {

    lateinit var appComponent: AppComponent
    private val cicerone = Cicerone.create()

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent
            .factory()
            .create(
                applicationContext,
                cicerone.router,
                cicerone.getNavigatorHolder()
            )
    }
}