package com.wasilyk.app.history.di

import com.wasilyk.app.history.HistoryDataSource
import com.wasilyk.app.history.HistoryDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface HistoryModule {

    @Binds
    fun bindHistoryDataSource(historyDataSourceImpl: HistoryDataSourceImpl): HistoryDataSource
}