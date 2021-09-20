package com.wasilyk.app.mydictionary.di.module

import com.wasilyk.app.mydictionary.view.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
interface AppModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity
}