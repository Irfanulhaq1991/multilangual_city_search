package com.example.citysearch.searching.domain

import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.data.CitySearchRepository

class SearchCityUseCase(
    private val citySearchRepository: CitySearchRepository,
    private val queryValidator: QueryValidator
) {
    suspend operator fun invoke (query: String):Result<List<City>>{
        if(!queryValidator.validate(query)) return Result.failure(Throwable("Please type valid query"))
        return citySearchRepository.searchCity(query)
    }

}
