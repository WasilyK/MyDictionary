package com.wasilyk.app.mydictionary.app

import android.app.Application
import com.wasilyk.app.mydictionary.model.datasource.DataSourceImpl
import com.wasilyk.app.mydictionary.presenter.MainPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class App : Application() {

    val presenter = MainPresenter(DataSourceImpl(), Schedulers.io(), AndroidSchedulers.mainThread())

    companion object {
        var instance: App? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}