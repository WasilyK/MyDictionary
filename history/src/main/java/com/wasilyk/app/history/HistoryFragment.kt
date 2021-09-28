package com.wasilyk.app.history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wasilyk.app.room_api.history.HistoryEntity
import com.wasilyk.app.core.entities.appstate.AppState
import com.wasilyk.app.core.entities.appstate.Error
import com.wasilyk.app.core.entities.appstate.Loading
import com.wasilyk.app.core.entities.appstate.Success
import com.wasilyk.app.history.databinding.FragmentHistoryBinding
import com.wasilyk.app.history.di.HistoryComponent
import com.wasilyk.app.history.di.HistoryComponentProvider
import javax.inject.Inject

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {

    private val data = mutableListOf<HistoryEntity>()
    private val historyAdapter = HistoryAdapter(data, this)
    private lateinit var historyComponent: HistoryComponent
    @Inject
    lateinit var historyViewModelFactory: HistoryViewModelFactory
    private lateinit var historyViewModel: HistoryViewModel

    private var _viewBinding: FragmentHistoryBinding? = null
    private val viewBinding get() = _viewBinding!!

    companion object {
        fun newInstance(): Fragment =
            HistoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        historyComponent = (requireActivity().application as HistoryComponentProvider)
            .provideHistoryComponent()
        historyComponent.inject(this)

        super.onCreate(savedInstanceState)

        historyViewModel = ViewModelProvider(this, historyViewModelFactory)
            .get(HistoryViewModel::class.java)
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
                data.addAll(appState.value as List<com.wasilyk.app.room_api.history.HistoryEntity>)
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