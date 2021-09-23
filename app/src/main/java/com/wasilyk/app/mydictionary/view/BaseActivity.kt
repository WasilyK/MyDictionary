package com.wasilyk.app.mydictionary.view

import androidx.appcompat.app.AppCompatActivity
import com.wasilyk.app.mydictionary.model.appstate.AppState
import com.wasilyk.app.mydictionary.viewmodel.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T>
    abstract fun renderData(appState: T)
}