package com.wasilyk.app.history.di

import com.wasilyk.app.history.HistoryFragment
import dagger.Subcomponent

@HistoryScope
@Subcomponent(modules = [HistoryModule::class])
interface HistoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HistoryComponent
    }

    fun inject(historyFragment: HistoryFragment)
}