package com.example.citysearch

import com.example.citysearch.data.CityDto
import com.example.citysearch.data.CoordinatesDto
import com.example.citysearch.domain.CityMapper
import com.example.citysearch.domain.IMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream
import java.io.IOException
import java.lang.RuntimeException
import java.text.Collator
import java.util.*

object TestDataProvider {
    private const val ASSET_BASE_PATH = "../app/src/main/assets/"

    fun provideDTOS(): List<CityDto> {
        return parsJsonBody(readFileFromAssets()!!).subList(0,500)
    }


    fun provideDomainModels(): List<City> {
        return map(provideDTOS())
    }
    private fun map(cities: List<CityDto>): List<City> {

        val mapper = CityMapper()
        return mapper.map(cities)
    }

    fun sort(cities:List<City>):List<City>{
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.cityName})
    }

   private fun readFileFromAssets(): String? {

        return try {
            val fileInputStream = FileInputStream(ASSET_BASE_PATH + "json.rtf")
            val size: Int = fileInputStream.available()
           val buffer = ByteArray(size)
            fileInputStream.read(buffer)
            fileInputStream.close()
            String(buffer)

        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

   private fun parsJsonBody(json: String): List<CityDto> {
        val typToken = object : TypeToken<List<CityDto>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, typToken)
    }

}