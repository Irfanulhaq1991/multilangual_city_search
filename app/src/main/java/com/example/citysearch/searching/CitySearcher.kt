package com.example.citysearch.searching

import com.example.citysearch.common.CITY_LIST_KEY
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.normalize
import java.lang.NullPointerException
import java.util.function.ToDoubleBiFunction


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


    private fun doSearch(query: String, data: List<City>): List<City> {
        var left = 0
        var right = data.size - 1
        var middle = (left + right) / 2
        val result = mutableListOf<City>()
        val i = query.length
        val normalizedQuery = query.normalize()
        while (left <= right) {
            if (data[middle].cityName.contains(normalizedQuery)) {
                val left = getLeftIndex()
                val rightIndex = getRight()

                return data.subList(left,rightIndex+1)
            }
            if (normalizedQuery > data[middle].cityName.substring(0, i)) {
                left = middle + 1

            } else if (normalizedQuery < data[middle].cityName.substring(0, i)) {
                right = middle - 1
            }
            middle = (left + right) / 2
        }
        return emptyList()
    }

    private fun getRight(): Int {
         return 4
    }

    private fun getLeftIndex():Int{
        return 0
    }

}
