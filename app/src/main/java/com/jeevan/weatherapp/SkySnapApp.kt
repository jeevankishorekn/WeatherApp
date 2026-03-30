package com.jeevan.weatherapp

import android.app.Application
import com.jeevan.weatherapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SkySnapApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SkySnapApp)
            modules(appModule)
        }
    }
}
