package com.wasilyk.app.mydictionary.presenter

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(
    private val dataSource: DataSource,
    private val subscribeOn: Scheduler,
    private val observeOn: Scheduler) {

    private var view: MainView? = null

    fun attachActivity(view: MainView) {
        this.view = view
    }

    fun detachActivity() {
        view = null
    }

    fun showWordDefinition() {
        val word = view?.getWord() ?: ""
        if(word.isBlank()) {
            view?.showErrorText()
            return
        }
        dataSource.getListWordDefinition(word)
            .subscribeOn(subscribeOn)
            .observeOn(observeOn)
            .subscribe(
                { wordDefinitions ->
                    val definition = wordDefinitions[0].meanings[0].definitions[0].definition
                    view?.showWordDefinition(definition)
                },
                { throwable ->
                    view?.showToast(throwable.message ?: "Unknown error")
                }
            )
    }

}