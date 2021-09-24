package com.wasilyk.app.mydictionary.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import coil.Coil
import coil.load
import coil.transform.RoundedCornersTransformation
import com.wasilyk.app.mydictionary.R
import com.wasilyk.app.mydictionary.databinding.FragmentMainBinding
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Loading
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding: FragmentMainBinding
        get() = _viewBinding!!
    private val mainViewModel: MainViewModel by inject()

    private var imageUrl: String? = null

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFavoriteButton()

        mainViewModel.subscribe().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        mainViewModel.imageUrlLiveData.observe(viewLifecycleOwner) { url ->
            if (url == "error") {
                viewBinding.image.load(R.drawable.ic_baseline_no_photography_24)
            } else {
                imageUrl = url
                viewBinding.image.load(imageUrl) {
                    placeholder(R.drawable.ic_baseline_no_photography_24)
                    crossfade(1500)
                    transformations(RoundedCornersTransformation(10F))
                }
            }
        }

        mainViewModel.favoriteButtonLiveData.observe(viewLifecycleOwner) { isNotShow ->
            if (!isNotShow) {
                showFavoriteButton()
            } else {
                hideFavoriteButton()
            }
        }

        viewBinding.etLayout.setEndIconOnClickListener {
            val word = viewBinding.wordEt.text.toString()
            if (word.isNotBlank()) {
                mainViewModel.getData(word, true)
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

    private fun initFavoriteButton() {
        viewBinding.etLayout.apply {
            isStartIconVisible = false
            setStartIconOnClickListener {
                val title = viewBinding.wordEt.text.toString()
                val definition = viewBinding.definitionTv.text.toString()
                mainViewModel.saveToFavorite(title, definition, imageUrl!!)
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is Loading -> {
                viewBinding.definitionTv.text = getString(R.string.loading_text)
            }
            is Success<*> -> {
                val wordDefinition = appState.value as String
                viewBinding.apply {
                    definitionTv.text = wordDefinition
                }

            }
            is Error -> {
                viewBinding.definitionTv.text = appState.throwable.message
            }
        }
    }

    private fun showFavoriteButton() {
        viewBinding.etLayout.isStartIconVisible = true
    }

    private fun hideFavoriteButton() {
        viewBinding.etLayout.isStartIconVisible = false
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}