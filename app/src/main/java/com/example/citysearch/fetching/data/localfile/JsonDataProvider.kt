package com.example.citysearch.fetching.data.localfile

import com.example.citysearch.fetching.data.CityDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class JsonDataProvider() {

    /**
     * Ge jsonString from asset file
     * */
   abstract fun getJsonCitiesFromAssets():String?

    /**
     * Deserialize the Json to DTO list
     * */
   open fun deSerializeAllCitiesJson(json: String): List<CityDto> {
        val typToken = object : TypeToken<List<CityDto>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, typToken)
    }

}