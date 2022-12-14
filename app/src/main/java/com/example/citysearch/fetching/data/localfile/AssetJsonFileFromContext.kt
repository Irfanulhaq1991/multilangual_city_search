package com.example.citysearch.fetching.data.localfile

import android.content.Context

/**
 * Get the Json from asset in the production application with [Context]
 * */
class AssetJsonFileFromContext(private val context: Context) : JsonDataProvider() {

    override fun getJsonCitiesFromAssets(): String? {

        val fileInputStream = context.assets.open("cities.json")
        val size: Int = fileInputStream.available()
        val buffer = ByteArray(size)
        fileInputStream.read(buffer)
        fileInputStream.close()
        return String(buffer)
    }

}