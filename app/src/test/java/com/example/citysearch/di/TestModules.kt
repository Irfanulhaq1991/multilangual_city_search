package com.example.citysearch.di

import com.example.citysearch.fetching.data.localfile.JsonDataProvider
import org.koin.dsl.module
import java.io.FileInputStream


/** Test module for helping koin to test if the production module can be loaded  by koin*/
val fetchCitiesModuleForTesting = module {
    factory<JsonDataProvider>  { providerJsonFileFromPath() }
}


private fun providerJsonFileFromPath():JsonDataProvider{
    return object:JsonDataProvider(){
        override fun getJsonCitiesFromAssets(): String {
            return "##"
        }
    }
}