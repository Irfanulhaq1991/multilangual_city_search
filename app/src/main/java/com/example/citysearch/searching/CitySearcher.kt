package com.example.citysearch.searching

import com.example.citysearch.common.CITY_LIST_KEY
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.normalize
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


    private fun doSearch(query: String, cachedData: List<City>): List<City> {
        var left = 0
        var right = cachedData.size - 1
        var middle = (left + right) / 2
        val result = mutableListOf<City>()
        val i = query.length
        val normalizedQuery = query.normalize()
        while (left <= right) {
            if (cachedData[middle].cityName.contains(normalizedQuery)) {
                result.add(cachedData[middle])
                return result
            }
            if (normalizedQuery > cachedData[middle].cityName.substring(0, i)) {
                left = middle + 1

            } else if (normalizedQuery < cachedData[middle].cityName.substring(0, i)) {
                right = middle - 1
            }
            middle = (left + right) / 2
        }
        return emptyList()
    }

}
