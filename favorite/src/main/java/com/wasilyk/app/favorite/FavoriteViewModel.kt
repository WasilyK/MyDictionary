package com.wasilyk.app.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasilyk.app.room_api.favorite.FavoriteEntity
import com.wasilyk.app.core.entities.appstate.AppState
import com.wasilyk.app.core.entities.appstate.Error
import com.wasilyk.app.core.entities.appstate.Success
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
            val data = withContext(Dispatchers.IO) {
                interactor.selectAllFavorites()
            }
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