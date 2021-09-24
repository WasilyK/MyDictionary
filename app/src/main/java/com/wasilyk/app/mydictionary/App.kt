package com.wasilyk.app.mydictionary

import android.app.Application
import com.wasilyk.app.mydictionary.di.module.applicationModule
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@App)
            modules(listOf(applicationModule))
        }
    }
}