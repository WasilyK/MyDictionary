package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteEntity
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.model.interactor.FavoriteInteractor
import kotlinx.coroutines.*

class FavoriteViewModel(private val interactor: FavoriteInteractor) : ViewModel() {

    private val _liveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState> get() = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _liveData.postValue(Error(exception))
    }
    private val scope = CoroutineScope(Dispatchers.IO + exceptionHandler)

    fun getData() {
        scope.launch {
            val data = interactor.selectAllFavorites()
            _liveData.postValue(Success(data))
        }
    }

    fun itemDelete(favoriteEntity: FavoriteEntity) {
        scope.launch {
            interactor.delete(favoriteEntity)
            getData()
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}