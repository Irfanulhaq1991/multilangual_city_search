package com.example.citysearch.searching

import com.example.citysearch.fetching.domain.City

class CitySearchRepository(private val citySearcher: CitySearcher) {
    fun searchCity(query:String):Result<List<City>> {
        return citySearcher.searchCity(query)
    }

}
