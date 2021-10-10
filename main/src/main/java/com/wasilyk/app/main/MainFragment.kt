package com.wasilyk.app.main

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.wasilyk.app.core.entities.appstate.AppState
import com.wasilyk.app.core.entities.appstate.Error
import com.wasilyk.app.core.entities.appstate.Loading
import com.wasilyk.app.core.entities.appstate.Success
import com.wasilyk.app.main.databinding.FragmentMainBinding
import com.wasilyk.app.main.di.MainComponent
import com.wasilyk.app.main.di.MainComponentProvider
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding: FragmentMainBinding
        get() = _viewBinding!!

    @Inject lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var mainComponent: MainComponent
    private lateinit var mainViewModel: MainViewModel

    private var imageUrl: String? = null

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (requireActivity().application as MainComponentProvider)
            .provideMainComponent()
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
        Log.d("TAG", mainViewModel.toString())
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

        mainViewModel.liveData.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        mainViewModel.imageUrlLiveData.observe(viewLifecycleOwner) { url ->
            renderImageUrl(url)
        }

        mainViewModel.favoriteButtonLiveData.observe(viewLifecycleOwner) { isNotShouldShow ->
            renderShouldShowFavoriteButton(isNotShouldShow)
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
                mainViewModel.saveToFavorite(title, definition, imageUrl)
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

    private fun renderShouldShowFavoriteButton(isNotShouldShow: Boolean) {
        viewBinding.etLayout.isStartIconVisible = !isNotShouldShow
    }

    private fun renderImageUrl(url: String) {
        if (url == "error") {
            viewBinding.image.load(R.drawable.ic_baseline_no_photography_24)
        } else {
            imageUrl = url
            viewBinding.image.load(imageUrl) {
                placeholder(R.drawable.ic_baseline_no_photography_24)
                crossfade(1500)
                transformations(RoundedCornersTransformation(10F))
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = RenderEffect.createBlurEffect(
                    15f,0f, Shader.TileMode.MIRROR
                )
                viewBinding.image.setRenderEffect(blurEffect)
            }
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}