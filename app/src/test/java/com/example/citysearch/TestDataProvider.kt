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

object TestDataProvider {
    private const val ASSET_BASE_PATH = "../app/src/main/assets/"

    fun provideDTOS(heavyData: Boolean = false): List<CityDto> {

        if (!heavyData)
            return listOf(

                CityDto(
                    "UK", "London", 12345,
                    CoordinatesDto(1.0, 1.0)
                ),
                CityDto(
                    "UK", "Yorkshire", 123456,
                    CoordinatesDto(2.0, 2.0)
                ),

                )
        return parsJsonBody(readFileFromAssets()!!)
    }


    fun provideDomainModels(heavyData: Boolean = false): List<City> {
        if (!heavyData)
            return listOf(
                City(
                    12345, "London", "UK",
                    Coordinates(1.0, 1.0)
                ),
                City(
                    123456, "Yorkshire", "UK",
                    Coordinates(2.0, 2.0)
                ),
            )

        return map(provideDTOS(heavyData))
    }


    fun map(cities: List<CityDto>): List<City> {
        return cities.map {
            City(
                it._id, it.name, it.country,
                Coordinates(it.coord.lon, it.coord.lat)
            )
        }
    }


    fun readFileFromAssets(): String? {
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

    fun parsJsonBody(json: String): List<CityDto> {
        val typToken = object : TypeToken<List<CityDto>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, typToken)
    }

}