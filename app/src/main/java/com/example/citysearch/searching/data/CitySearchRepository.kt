package com.example.citysearch.searching.data

import com.example.citysearch.fetching.domain.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitySearchRepository(private val citySearcher: ICitySearcher) {
    suspend fun searchCity(query: String) =
        withContext(Dispatchers.Default) {
            citySearcher.searchCity(query)
        }
}
