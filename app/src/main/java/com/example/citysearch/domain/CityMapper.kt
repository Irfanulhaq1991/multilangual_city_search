package com.example.citysearch.domain

import com.example.citysearch.data.CityDto
import java.text.Collator
import java.text.Normalizer
import java.util.*

class CityMapper : IMapper<List<CityDto>, List<City>> {
    override fun map(cities: List<CityDto>): List<City> {
        val mappedResult =  cities.map {
            City(
                it._id, normalizeCityName(it.name), it.country,
                Coordinates(it.coord.lon, it.coord.lat)
            )
        }
        return sortingRule(mappedResult)
    }

     // string normalization for easy sorting and searching
     // https://docs.oracle.com/javase/tutorial/i18n/text/normalizerapi.html
     //https://docs.oracle.com/cd/B19306_01/server.102/b14225/ch5lingsort.htm#i1008198
    private fun normalizeCityName(name: String): String {
        return Normalizer.normalize(name, Normalizer.Form.NFD)
    }

    // Applying Business rule
    private fun sortingRule(cities:List<City>):List<City>{
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.cityName})
    }

}