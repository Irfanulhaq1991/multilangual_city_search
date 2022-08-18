package com.example.citysearch.domain

import com.example.citysearch.City
import com.example.citysearch.data.CitiesRepository

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository) {

    // No clear idea at the moment e.g like item per page
    suspend operator fun invoke ():Result<List<City>> {
       return citiesRepository.fetchCities()
     }
}
