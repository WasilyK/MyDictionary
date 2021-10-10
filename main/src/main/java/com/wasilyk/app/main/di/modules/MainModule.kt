package com.wasilyk.app.main.di.modules

import com.wasilyk.app.main.MainDataSource
import com.wasilyk.app.main.MainDataSourceImpl
import com.wasilyk.app.main.di.MainScope
import dagger.Binds
import dagger.Module

@Module
interface MainModule {

    @MainScope
    @Binds
    fun bindMainDataSource(mainDataSourceImpl: MainDataSourceImpl): MainDataSource
}