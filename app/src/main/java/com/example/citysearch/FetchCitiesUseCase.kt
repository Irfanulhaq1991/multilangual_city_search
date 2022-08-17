package com.example.citysearch

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository) {

    // No clear idea at the moment e.g like item per page
    operator fun invoke () {
        citiesRepository.fetchCities()
     }
}
