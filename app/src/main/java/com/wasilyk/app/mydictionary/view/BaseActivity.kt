package com.wasilyk.app.mydictionary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wasilyk.app.mydictionary.model.appstate.AppState
import com.wasilyk.app.mydictionary.viewmodel.BaseViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<T : AppState> : AppCompatActivity(), HasAndroidInjector {

    abstract val viewModel: BaseViewModel<T>
    abstract fun renderData(appState: T)

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector() = androidInjector
}