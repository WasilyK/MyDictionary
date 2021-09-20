package com.wasilyk.app.mydictionary.di.component

import android.content.Context
import com.wasilyk.app.mydictionary.App
import com.wasilyk.app.mydictionary.di.module.AppModule
import com.wasilyk.app.mydictionary.di.module.DataSourceModule
import com.wasilyk.app.mydictionary.di.module.RetrofitModule
import com.wasilyk.app.mydictionary.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        DataSourceModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}