package com.example.citysearch.domain

import com.example.citysearch.City
import com.example.citysearch.data.CitiesRepository
import java.text.Collator
import java.util.*

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke ():Result<List<City>> {
       return citiesRepository.fetchCities().map { sortingRul(it) }
     }

    // Applying Business rule
 private fun sortingRul(cities:List<City>):List<City>{
        val usCollator: Collator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return cities.sortedWith(compareBy(usCollator) { it.cityName})
    }
}

