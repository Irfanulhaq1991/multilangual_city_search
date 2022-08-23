package com.example.citysearch.data.localfile

import android.content.Context

class AssetFileFromContext(private val context: Context) : JsonDataProvider() {

    override fun getJsonCitiesFromAssets(): String? {

        val fileInputStream = context.assets.open("cities.json")
        val size: Int = fileInputStream.available()
        val buffer = ByteArray(size)
        fileInputStream.read(buffer)
        fileInputStream.close()
        return String(buffer)
    }

}