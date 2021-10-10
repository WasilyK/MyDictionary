package com.wasilyk.app.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wasilyk.app.main.di.MainScope
import com.wasilyk.app.room_api.favorite.FavoriteDao
import com.wasilyk.app.room_api.history.HistoryDao
import java.lang.IllegalArgumentException
import javax.inject.Inject

@MainScope
class MainViewModelFactory @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val historyDao: HistoryDao,
    private val favoriteDao: FavoriteDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when(modelClass) {
            MainViewModel::class.java -> MainViewModel(mainInteractor, historyDao, favoriteDao)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
}