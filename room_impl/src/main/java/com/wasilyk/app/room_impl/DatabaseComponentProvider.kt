package com.wasilyk.app.room_impl

interface DatabaseComponentProvider {
    fun provideDatabaseComponent(): DatabaseComponent
}