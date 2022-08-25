package com.example.citysearch.searching

import com.example.citysearch.fetching.domain.City

class SearchCityUseCase(private val citySearchRepository: CitySearchRepository) {
    suspend operator fun invoke (query: String):Result<List<City>>{
        return citySearchRepository.searchCity(query)
    }

}
