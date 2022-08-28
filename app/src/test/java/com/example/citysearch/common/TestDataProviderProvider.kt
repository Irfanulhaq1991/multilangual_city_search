package com.example.citysearch.common

import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.localfile.JsonDataProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.CityMapper
import java.io.FileInputStream
import java.text.Collator
import java.util.*

object TestDataProviderProvider : JsonDataProvider() {


    fun provideDTOS(): List<CityDto> {
        return deSerializeAllCitiesJson(getJsonCitiesFromAssets()).subList(0, 500)
    }

    fun provideDomainModels(): List<City> {
        return map(provideDTOS())
    }

    // Map the dto to domain for testing
    private fun map(cities: List<CityDto>): List<City> {
        val mapper = CityMapper()
        return mapper.map(cities)
    }

    // sorting DomainModels for testing
    fun sortDomainModels(cities: List<City>): List<City> {
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.cityName })
    }

    // sorting DtoModels for testing
    fun sortDto(cities: List<CityDto>): List<CityDto> {
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.name })
    }

    override fun getJsonCitiesFromAssets(): String {
        val ASSET_BASE_PATH = "../app/src/main/assets/"
        val fileInputStream = FileInputStream(ASSET_BASE_PATH + "cities.json")
        val size: Int = fileInputStream.available()
        val buffer = ByteArray(size)
        fileInputStream.read(buffer)
        fileInputStream.close()
        return String(buffer)
    }

}
