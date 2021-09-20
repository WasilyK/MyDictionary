package com.wasilyk.app.mydictionary.di.module

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.DataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface DataSourceModule {

    @Reusable
    @Binds
    fun bindDataSource(dataSourceImpl: DataSourceImpl): DataSource
}