package com.example.citysearch.di

import com.example.citysearch.data.localfile.JsonDataProvider
import org.koin.dsl.module
import java.io.FileInputStream

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
open class AssetJsonFileFromPath:JsonDataProvider() {
    private val ASSET_BASE_PATH = "../app/src/main/assets/"
    override fun getJsonCitiesFromAssets(): String? {
        val fileInputStream = FileInputStream(ASSET_BASE_PATH + "cities.json")
        val size: Int = fileInputStream.available()
        val buffer = ByteArray(size)
        fileInputStream.read(buffer)
        fileInputStream.close()
        return String(buffer)
    }
}