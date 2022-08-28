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
        var querLength = query.length
        var i = querLength
        val normalizedQuery = query.normalize()

        while (left <= right) {
            if (i > data[middle].cityName.length)
                i = data[middle].cityName.length
            if (data[middle].cityName.substring(0, i) == normalizedQuery) {
                val leftIndex = getLeftIndex(query, data, left, middle)
                val rightIndex = getRight(query, data, right, middle)

                return data.subList(leftIndex, rightIndex + 1)
            }
            if (normalizedQuery > data[middle].cityName.substring(0, i)) {
                left = middle + 1

            } else if (normalizedQuery < data[middle].cityName.substring(0, i)) {
                right = middle - 1
            }
            middle = (left + right) / 2
            i = normalizedQuery.length
        }
        return emptyList()
    }

    private fun getRight(query: String, data: List<City>, rightIndex: Int, mainMiddle: Int): Int {
        var right = rightIndex
        var left = mainMiddle + 1
        var middle = (left + right) / 2
        val i = query.length
        if (mainMiddle == data.size - 1) return right
        else if (data[mainMiddle + 1].cityName.substring(0, i) > query) {
            return mainMiddle
        } else {
            while (left <= right) {
                if (data[middle].cityName.substring(0, i) > query) {
                    right = middle - 1
                } else
                    left = middle + 1
                middle = (left + right) / 2
            }
        }
        return right
    }

    private fun getLeftIndex(
        query: String,
        data: List<City>,
        leftIndex: Int,
        mainMiddle: Int
    ): Int {
        var left = leftIndex
        var right = mainMiddle - 1
        var middle = (left + right) / 2
        val i = query.length
        if (mainMiddle == 0) return left
        else if (data[mainMiddle - 1].cityName.substring(0, i) < query) {
            return mainMiddle
        } else {
            while (left <= right) {
                if (data[middle].cityName.substring(0, i) < query) {
                    left = middle + 1
                } else
                    right = middle - 1
                middle = (left + right) / 2
            }
        }
        return left
    }

}
