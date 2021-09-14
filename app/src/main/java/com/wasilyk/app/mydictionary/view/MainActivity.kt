package com.wasilyk.app.mydictionary.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wasilyk.app.mydictionary.R
import com.wasilyk.app.mydictionary.app.App
import com.wasilyk.app.mydictionary.databinding.ActivityMainBinding
import com.wasilyk.app.mydictionary.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var viewBinding: ActivityMainBinding
    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        presenter = App.instance?.presenter
        presenter?.attachActivity(this)

        viewBinding.etLayout.setEndIconOnClickListener {
            presenter?.showWordDefinition()
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

    override fun onDestroy() {
        presenter?.detachActivity()
        super.onDestroy()
    }

    override fun showWordDefinition(definition: String) {
        viewBinding.definitionTv.text = definition
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getWord(): String = viewBinding.wordEt.text.toString()

    override fun showErrorText() {
        viewBinding.etLayout.error = resources.getString(R.string.error_text)
    }
}