package com.example.citysearch.di

import com.example.citysearch.data.localfile.AssetFileFromPath
import com.example.citysearch.data.localfile.JsonDataProvider
import org.koin.dsl.module

val fetchCitiesModuleForTesting = module {
    factory<JsonDataProvider>  { AssetFileFromPath() }
}