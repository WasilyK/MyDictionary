package com.wasilyk.app.mydictionary.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.wasilyk.app.mydictionary.R
import com.wasilyk.app.mydictionary.databinding.ActivityMainBinding
import com.wasilyk.app.mydictionary.model.appstate.AppState
import com.wasilyk.app.mydictionary.model.appstate.Error
import com.wasilyk.app.mydictionary.model.appstate.Loading
import com.wasilyk.app.mydictionary.model.appstate.Success
import com.wasilyk.app.mydictionary.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<AppState>() {

    private lateinit var viewBinding: ActivityMainBinding
    override val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel.subscribe().observe(this) { appState ->
            renderData(appState)
        }

        viewBinding.etLayout.setEndIconOnClickListener {
            val word = viewBinding.wordEt.text.toString()
            if(word.isNotBlank()) {
                viewModel.getData(word, true)
            } else {
                viewBinding.etLayout.error = resources.getString(R.string.error_text)
            }
        }

        viewBinding.wordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewBinding.etLayout.error?.let {
                    viewBinding.etLayout.error = null
                }
            }
        })
    }

    override fun renderData(appState: AppState) {
        when(appState) {
            is Loading -> {
                viewBinding.definitionTv.text = getString(R.string.loading_text)
            }
            is Success -> {
                viewBinding.definitionTv.text = appState.wordDefinition
            }
            is Error -> {
                viewBinding.definitionTv.text = appState.throwable.message
            }
        }
    }
}