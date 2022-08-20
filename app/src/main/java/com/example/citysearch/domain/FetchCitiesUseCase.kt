package com.example.citysearch.domain

import com.example.citysearch.City
import com.example.citysearch.data.CitiesRepository

class FetchCitiesUseCase(private val citiesRepository: CitiesRepository, val pager: Pager) {

    suspend operator fun invoke ():Result<List<City>> {
        val currentPage = pager.getNextPage()
        val pageSize = pager.getPageSize()

       return citiesRepository.fetchCities(pageSize,pageSize).also { it.map { pager.setCurrentPage(currentPage) } }
     }


}

