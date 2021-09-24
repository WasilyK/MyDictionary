package com.wasilyk.app.mydictionary.di.module

import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.datasource.DataSourceImpl
import com.wasilyk.app.mydictionary.model.datasource.retrofit.RetrofitApi
import com.wasilyk.app.mydictionary.model.datasource.retrofit.pexel.PexelsApi
import com.wasilyk.app.mydictionary.model.datasource.retrofit.pexel.PexelsApiFactory
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteDao
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryDao
import com.wasilyk.app.mydictionary.model.datasource.room.MyDataBase
import com.wasilyk.app.mydictionary.model.interactor.FavoriteInteractor
import com.wasilyk.app.mydictionary.model.interactor.HistoryInteractor
import com.wasilyk.app.mydictionary.model.interactor.MainInteractor
import com.wasilyk.app.mydictionary.view.FavoriteFragment
import com.wasilyk.app.mydictionary.view.HistoryFragment
import com.wasilyk.app.mydictionary.view.MainFragment
import com.wasilyk.app.mydictionary.viewmodel.FavoriteViewModel
import com.wasilyk.app.mydictionary.viewmodel.HistoryViewModel
import com.wasilyk.app.mydictionary.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DB_NAME = "app_data.db"
const val MAIN_FRAGMENT_SCREEN = "main_fragment_screen"
const val HISTORY_FRAGMENT_SCREEN = "history_fragment_screen"
const val FAVORITE_FRAGMENT_SCREEN = "favorite_fragment_screen"
private val cicerone = Cicerone.create()

val applicationModule = module {
    single<RetrofitApi> { RetrofitModule().provideRetrofitApi() }
    single<PexelsApi> { PexelsApiFactory().createPexelApi() }
    single<DataSource> { DataSourceImpl(
        retrofitApi = get(), historyDao = get(), pexelsApi = get(), favoriteDao = get()) }
    single<MainInteractor> { MainInteractor(dataSource = get()) }
    single<HistoryInteractor> { HistoryInteractor(dataSource = get()) }
    single<FavoriteInteractor> { FavoriteInteractor(dataSource = get()) }
    single<MainViewModel> {
        MainViewModel(interactor = get(), historyDao = get(), favoriteDao = get()) }
    single<HistoryViewModel> { HistoryViewModel(interactor = get()) }
    single<FavoriteViewModel> { FavoriteViewModel(interactor = get())}
    single<MyDataBase> { Room.databaseBuilder(androidContext(), MyDataBase::class.java, DB_NAME).build() }
    single<HistoryDao> { get<MyDataBase>().getHistoryDao() }
    single<FavoriteDao> { get<MyDataBase>().getFavoriteDao() }
    single<NavigatorHolder> { cicerone.getNavigatorHolder() }
    single<Router> { cicerone.router }
    factory<Screen> (named(MAIN_FRAGMENT_SCREEN)) { FragmentScreen { MainFragment.newInstance() } }
    factory<Screen> (named(HISTORY_FRAGMENT_SCREEN)) { FragmentScreen { HistoryFragment.newInstance() } }
    factory<Screen> (named(FAVORITE_FRAGMENT_SCREEN)) { FragmentScreen { FavoriteFragment.newInstance() } }
}