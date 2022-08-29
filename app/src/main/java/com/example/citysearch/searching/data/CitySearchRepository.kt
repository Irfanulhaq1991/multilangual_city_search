package com.example.citysearch.searching.data

import com.example.citysearch.fetching.domain.City

class CitySearchRepository(private val citySearcher: ICitySearcher) {
    fun searchCity(query:String):Result<List<City>> {
        return citySearcher.searchCity(query)
    }

}
