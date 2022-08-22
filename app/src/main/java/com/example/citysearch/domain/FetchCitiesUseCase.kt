package com.example.citysearch.domain

import com.example.citysearch.City
import com.example.citysearch.data.CitiesRepository

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository, private val pager: Pager) {

    suspend operator fun invoke(direction: Int): Result<List<City>> {
        val currentPage = pager.getNextPage(direction)
        val pageSize = pager.getPageSize()

        return citiesRepository
            .fetchCities(currentPage, pageSize).also { }
            .map {
                pager.totalCount = it.second
                pager.currentPage = currentPage
                it.first
            }
    }


}

