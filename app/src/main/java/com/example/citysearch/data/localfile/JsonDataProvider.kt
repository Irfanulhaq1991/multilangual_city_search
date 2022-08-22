package com.example.citysearch.data.localfile

import com.example.citysearch.data.CityDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream

open class JsonDataProvider {
    private val ASSET_BASE_PATH = "../app/src/main/assets/"

   open fun getJsonCitiesFromAssets(): String? {
        val fileInputStream = FileInputStream(ASSET_BASE_PATH + "cities.json")
        val size: Int = fileInputStream.available()
        val buffer = ByteArray(size)
        fileInputStream.read(buffer)
        fileInputStream.close()
        return String(buffer)


    }

   open fun deSerializeAllCitiesJson(json: String): List<CityDto> {
        val typToken = object : TypeToken<List<CityDto>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, typToken)
    }

}
