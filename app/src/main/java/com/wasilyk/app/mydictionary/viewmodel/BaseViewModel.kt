package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasilyk.app.mydictionary.model.appstate.AppState
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<T : AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
    protected val disposables: CompositeDisposable = CompositeDisposable()
) : ViewModel() {

    open fun getData(word: String, isOnline: Boolean): LiveData<T> =
        liveData

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}