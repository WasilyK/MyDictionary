package com.wasilyk.app.mydictionary

import android.app.Application
import com.wasilyk.app.mydictionary.di.module.applicationModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin() {
            modules(listOf(applicationModule))
        }
    }
}