package com.example.citysearch.data.localfile

import java.io.FileInputStream

open class AssetFileFromPath:JsonDataProvider() {
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