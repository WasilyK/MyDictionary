package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.model.interactor.HistoryInteractor
import kotlinx.coroutines.*

class HistoryViewModel(
    private val interactor: HistoryInteractor
) : ViewModel() {

    private val _liveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState>
        get() = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _liveData.postValue(Error(exception))
    }
    private val scope = CoroutineScope(Dispatchers.IO + exceptionHandler)

    fun getData() {
        scope.launch {
            val histories = interactor.selectAllHistories()
            _liveData.postValue(Success(histories))
        }
    }

    fun deleteHistory(historyEntity: HistoryEntity) =
        scope.launch {
            interactor.delete(historyEntity)
            getData()
        }

    fun clearHistory() {
        scope.launch {
            interactor.clear()
            getData()
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}

