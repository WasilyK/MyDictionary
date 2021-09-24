package com.wasilyk.app.mydictionary.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wasilyk.app.mydictionary.databinding.FragmentFavoriteBinding
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteEntity
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Loading
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.viewmodel.FavoriteViewModel
import org.koin.android.ext.android.inject

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemDeleteClickListener {

    private var _viewBinding: FragmentFavoriteBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val data = mutableListOf<FavoriteEntity>()
    private val favoriteAdapter = FavoriteAdapter(data, this)

    private val viewModel: FavoriteViewModel by inject()

    companion object {
        fun newInstance(): Fragment = FavoriteFragment()
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
        viewModel.liveData.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        viewModel.getData()
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
                data.addAll(appState.value as List<FavoriteEntity>)
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
        viewModel.itemDelete(data[pos])
    }
}