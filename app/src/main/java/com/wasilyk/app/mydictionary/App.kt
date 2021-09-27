package com.wasilyk.app.mydictionary

import android.app.Application
import com.wasilyk.app.mydictionary.di.module.applicationModule
import com.wasilyk.app.mydictionary.di.module.favoriteModule
import com.wasilyk.app.mydictionary.di.module.historyModule
import com.wasilyk.app.mydictionary.di.module.mainModule
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@App)
            modules(listOf(applicationModule, mainModule, historyModule, favoriteModule))
        }
    }
}