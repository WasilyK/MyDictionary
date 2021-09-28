package com.wasilyk.app.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wasilyk.app.core.entities.appstate.AppState
import com.wasilyk.app.core.entities.appstate.Error
import com.wasilyk.app.core.entities.appstate.Loading
import com.wasilyk.app.core.entities.appstate.Success
import com.wasilyk.app.favorite.databinding.FragmentFavoriteBinding
import com.wasilyk.app.favorite.di.FavoriteComponent
import com.wasilyk.app.favorite.di.FavoriteComponentProvider
import javax.inject.Inject

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemDeleteClickListener {

    private var _viewBinding: FragmentFavoriteBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val data = mutableListOf<com.wasilyk.app.room_api.favorite.FavoriteEntity>()
    private val favoriteAdapter = FavoriteAdapter(data, this)

    @Inject lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private lateinit var favoriteComponent: FavoriteComponent
    private lateinit var favoritViewModel: FavoriteViewModel

    companion object {
        fun newInstance(): Fragment = FavoriteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        favoriteComponent = (requireActivity().application as FavoriteComponentProvider).provideFavoriteComponent()
        favoriteComponent.inject(this)
        super.onCreate(savedInstanceState)

        favoritViewModel = ViewModelProvider(this, favoriteViewModelFactory)
            .get(FavoriteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        favoritViewModel.liveData.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        favoritViewModel.getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderData(appState: AppState) {
        when(appState) {
            is Loading -> {
                viewBinding.apply {
                    rvFavorite.visibility = View.GONE
                    progressContainer.visibility = View.VISIBLE
                }
            }
            is Success<*> -> {
                viewBinding.apply {
                    rvFavorite.visibility = View.VISIBLE
                    progressContainer.visibility = View.GONE
                }
                data.clear()
                data.addAll(appState.value as List<com.wasilyk.app.room_api.favorite.FavoriteEntity>)
                favoriteAdapter.notifyDataSetChanged()
            }
            is Error -> {
                viewBinding.apply {
                    rvFavorite.visibility = View.VISIBLE
                    progressContainer.visibility = View.GONE
                }
                Toast.makeText(
                    requireContext(),
                    appState.throwable.message?: "Unknown error",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    override fun onItemDeleteClick(pos: Int) {
        favoritViewModel.itemDelete(data[pos])
    }
}