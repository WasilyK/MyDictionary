package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState

abstract class BaseViewModel<T : AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
) : ViewModel() {

    open fun getData(word: String, isOnline: Boolean): LiveData<T> =
        liveData
}