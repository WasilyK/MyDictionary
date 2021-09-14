package com.wasilyk.app.mydictionary.presenter

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MainPresenter(private val dataSource: DataSource) {

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
            .observeOn(AndroidSchedulers.mainThread())
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