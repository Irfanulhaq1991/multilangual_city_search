package com.example.citysearch.data.localfile

import android.content.Context
import com.example.citysearch.data.CityDto
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream

abstract class JsonDataProvider() {

   abstract fun getJsonCitiesFromAssets():String?

   open fun deSerializeAllCitiesJson(json: String): List<CityDto> {
        val typToken = object : TypeToken<List<CityDto>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, typToken)
    }

}