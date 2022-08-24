package com.example.citysearch.fetching.domain

import com.example.citysearch.fetching.data.CitiesRepository

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke(): Result<List<City>> {
        return citiesRepository.fetchCities()
    }


}

