package com.example.citysearch.data

import com.example.citysearch.data.localfile.JsonDataProvider
import com.example.citysearch.domain.City
import com.example.citysearch.domain.CityMapper
import java.text.Collator
import java.util.*

object TestDataProviderProvider: JsonDataProvider() {



    fun provideDTOS(): List<CityDto> {
        return deSerializeAllCitiesJson(getJsonCitiesFromAssets()!!).subList(0,500)
    }

    fun provideDomainModels(): List<City> {
        return map(provideDTOS())
    }
    private fun map(cities: List<CityDto>): List<City> {

        val mapper = CityMapper()
        return mapper.map(cities)
    }

    fun sortDomainModels(cities:List<City>):List<City>{
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.cityName})
    }

    fun sortDto(cities:List<CityDto>):List<CityDto>{
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.name})
    }

}