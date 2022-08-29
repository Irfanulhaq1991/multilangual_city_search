package com.example.citysearch.common

import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.localfile.JsonDataProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.mapper.CityMapper
import java.io.FileInputStream
import java.text.Collator
import java.util.*

object TestDataProviderProvider : JsonDataProvider() {


    fun provideDOSFromBeginning(): List<CityDto> {
         return deSerializeAllCitiesJson(getJsonCitiesFromAssets()).subList(0,900)
    }

    fun provideDTOSFromEnding(): List<CityDto> {
        val data = deSerializeAllCitiesJson(getJsonCitiesFromAssets())
        return data.subList(data.size-900,data.size)
    }


    fun provideDomainModelFromEnding(): List<City> {
        return map(provideDTOSFromEnding())
    }

    fun provideDomainModelsFromBeginning(): List<City> {
        return map(provideDOSFromBeginning())
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
