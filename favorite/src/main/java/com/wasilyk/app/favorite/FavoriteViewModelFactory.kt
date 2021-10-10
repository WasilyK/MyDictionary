package com.wasilyk.app.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wasilyk.app.favorite.di.FavoriteScope
import java.lang.IllegalArgumentException
import javax.inject.Inject

@FavoriteScope
class FavoriteViewModelFactory @Inject constructor(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when(modelClass) {
            FavoriteViewModel::class.java -> FavoriteViewModel(favoriteInteractor)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
}