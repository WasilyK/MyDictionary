package com.wasilyk.app.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wasilyk.app.history.di.HistoryScope
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HistoryScope
class HistoryViewModelFactory @Inject constructor(
    private val historyInteractor: HistoryInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when(modelClass) {
            HistoryViewModel::class.java -> HistoryViewModel(historyInteractor)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
}