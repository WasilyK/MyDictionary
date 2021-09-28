package com.wasilyk.app.room_impl

import dagger.Subcomponent

@Subcomponent(modules = [DatabaseModule::class])
interface DatabaseComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DatabaseComponent
    }
}