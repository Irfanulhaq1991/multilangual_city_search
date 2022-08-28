package com.example.citysearch.searching

import com.example.citysearch.common.CITY_LIST_KEY
import com.example.citysearch.fetching.domain.City
import java.lang.NullPointerException


class CitySearcher(private val cache: IAppCache<String, List<City>>) {
    fun searchCity(query: String): Result<List<City>> {
        return try {
            val cachedData = cache[CITY_LIST_KEY]
            val result = doSearch(query, cachedData)
            Result.success(result)
        } catch (e: NullPointerException) {
            Result.failure(Throwable("Error"))
        }
    }


    private fun doSearch(query: String, cachedData: List<City>):List<City>{
        return emptyList()

    }

}
