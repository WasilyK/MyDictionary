package com.wasilyk.app.mydictionary.di.module

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.DataSourceImpl
import com.wasilyk.app.mydictionary.model.datasource.retrofit.RetrofitApi
import com.wasilyk.app.mydictionary.model.interactor.MainInteractor
import com.wasilyk.app.mydictionary.viewmodel.MainViewModel
import org.koin.dsl.module

val applicationModule = module {
    single<RetrofitApi> { RetrofitModule().provideRetrofitApi() }
    single<DataSource> { DataSourceImpl(retrofitApi = get()) }
    single<MainInteractor> { MainInteractor(dataSource = get()) }
    single<MainViewModel> { MainViewModel(interactor = get()) }
}