package com.sami.weathertest

import android.app.Application
import com.sami.weathertest.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(viewModelModule,
                    repositoryModule,
                    dataSourceModule,
                    networkModule)
            )
        }
    }
}