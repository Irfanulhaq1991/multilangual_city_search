package com.example.citysearch.fetching.domain.mapper

import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.Coordinates
import java.text.Collator
import java.text.Normalizer
import java.util.*

class CityMapper : IMapper<List<CityDto>, List<City>> {
    override fun map(cities: List<CityDto>): List<City> {
        val mappedResult = cities.map {
            City(
                it._id, it.name.normalize(), it.country,
                Coordinates(it.coord.lon, it.coord.lat)
            )
        }
        return sortingRule(mappedResult)
    }


    // Applying Business rule
    private fun sortingRule(cities: List<City>): List<City> {
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.cityName })
    }

}

private val REGEX_UN_ACCENT = "\\p{M}|\\p{M}".toRegex()
private val REGEX_QUOTATION_MARK_BRACKET = "[`'’‘-]|[()]".toRegex()

// string normalization for easy sorting and searching
// https://docs.oracle.com/javase/tutorial/i18n/text/normalizerapi.html
//https://docs.oracle.com/cd/B19306_01/server.102/b14225/ch5lingsort.htm#i1008198
fun String.normalize():String{
    val normalizerName = Normalizer.normalize(this, Normalizer.Form.NFD)

    return normalizerName
        .replace(REGEX_UN_ACCENT, "")
        .replace(REGEX_QUOTATION_MARK_BRACKET, "")
        .replace("Ø", "O")
        .replace("Ħ", "H")
        .replace("Ł", "L")
        .replace("ł", "l")
     //   .replace("[()]".toRegex(), "")
}