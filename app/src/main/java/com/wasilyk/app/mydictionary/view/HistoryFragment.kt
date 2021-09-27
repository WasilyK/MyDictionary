package com.wasilyk.app.mydictionary.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wasilyk.app.mydictionary.R
import com.wasilyk.app.mydictionary.databinding.FragmentHistoryBinding
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Loading
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.viewmodel.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {

    private val data = mutableListOf<HistoryEntity>()
    private val historyAdapter = HistoryAdapter(data, this)
    private val historyViewModel: HistoryViewModel by viewModel()

    private var _viewBinding: FragmentHistoryBinding? = null
    private val viewBinding get() = _viewBinding!!

    companion object {
        fun newInstance(): Fragment =
            HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _viewBinding = FragmentHistoryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        historyViewModel.liveData.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        historyViewModel.getData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.clear -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmation")
                    .setMessage("Are you sure to clear all history?")
                    .setPositiveButton("OK",) { _, _ ->
                        historyViewModel.clearHistory()
                    }
                    .setNegativeButton("Cancel") { di, _ ->
                        di.cancel()
                    }
                    .create()
                    .show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.rvHistory.apply{
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false)
            adapter = historyAdapter
        }

    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderData(appState: AppState) {
        when(appState) {
            is Loading -> {
                viewBinding.apply {
                    rvHistory.visibility = View.GONE
                    progressContainer.visibility = View.VISIBLE
                }
            }
            is Success<*> -> {
                viewBinding.apply {
                    rvHistory.visibility = View.VISIBLE
                    progressContainer.visibility = View.GONE
                }
                data.clear()
                data.addAll(appState.value as List<HistoryEntity>)
                viewBinding.rvHistory.adapter?.notifyDataSetChanged()
            }
            is Error -> {
                viewBinding.apply {
                    rvHistory.visibility = View.VISIBLE
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

    override fun itemClick(pos: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage("Are you sure to delete this record?")
            .setPositiveButton("OK",) { _, _ ->
                historyViewModel.deleteHistory(data[pos])
            }
            .setNegativeButton("Cancel") { di, _ ->
                di.cancel()
            }
            .create()
            .show()

    }
}