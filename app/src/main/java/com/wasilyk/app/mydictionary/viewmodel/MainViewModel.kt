package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import com.wasilyk.app.mydictionary.model.appstate.AppState
import com.wasilyk.app.mydictionary.model.appstate.Error
import com.wasilyk.app.mydictionary.model.appstate.Loading
import com.wasilyk.app.mydictionary.model.appstate.Success
import com.wasilyk.app.mydictionary.model.interactor.MainInteractor
import io.reactivex.rxjava3.kotlin.plusAssign
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        disposables += interactor.getData(word, isOnline)
            .doOnSubscribe { liveData.postValue(Loading) }
            .subscribe(
                { wordDefinitions ->
                    val wordDefinition = wordDefinitions[0].meanings[0].definitions[0].definition
                    liveData.postValue(Success(wordDefinition))
                },
                { throwable ->
                    liveData.postValue(Error(throwable))
                }
            )

        return super.getData(word, isOnline)
    }

    fun subscribe(): LiveData<AppState> = liveData
}