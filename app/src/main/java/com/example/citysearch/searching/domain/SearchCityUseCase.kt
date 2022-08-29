package com.example.citysearch.searching.domain

import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.data.CitySearchRepository

class SearchCityUseCase(private val citySearchRepository: CitySearchRepository) {
    suspend operator fun invoke (query: String):Result<List<City>>{
        return citySearchRepository.searchCity(query)
    }

}
