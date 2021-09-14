package com.wasilyk.app.mydictionary.app

import android.app.Application
import com.wasilyk.app.mydictionary.model.datasource.DataSourceImpl
import com.wasilyk.app.mydictionary.presenter.MainPresenter

class App : Application() {

    val presenter = MainPresenter(DataSourceImpl())

    companion object {
        var instance: App? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}