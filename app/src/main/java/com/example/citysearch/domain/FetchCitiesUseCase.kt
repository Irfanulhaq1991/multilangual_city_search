package com.example.citysearch.domain

import com.example.citysearch.City
import com.example.citysearch.data.CitiesRepository

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke ():Result<List<City>> {
       return citiesRepository.fetchCities(-1,-1)
     }


}

