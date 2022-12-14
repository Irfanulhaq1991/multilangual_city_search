package com.example.citysearch.common

import android.app.Application
import com.example.citysearch.di.fetchCitiesModule
import com.example.citysearch.di.fetchCitiesModuleWithContext
import com.example.citysearch.di.searchCityModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger(Level.ERROR)
            //inject Android context
            androidContext(this@App)
            // use modules
            modules(fetchCitiesModule, searchCityModule, fetchCitiesModuleWithContext)
        }
    }

}