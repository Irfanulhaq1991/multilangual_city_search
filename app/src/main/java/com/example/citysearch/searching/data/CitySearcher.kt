package com.example.citysearch.searching.data

import com.example.citysearch.common.CITY_LIST_KEY
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.mapper.normalize




class CitySearcher(private val cache: IAppCache<String, List<City>>) : ICitySearcher {


    /**
     * Get data from [IAppCache] and  filter the data based on the input string
     */
    override fun searchCity(query: String): Result<List<City>> {
        return try {
            val cachedData = cache[CITY_LIST_KEY]
            val result = doSearch(query, cachedData)
            Result.success(result)
        } catch (e: NullPointerException) {
            Result.failure(Throwable("Error: Cache Corrupted"))
        }
    }

    /**
     * filter the provide data based on the input string
     */
    private fun doSearch(query: String, data: List<City>): List<City> {
        var left = 0
        var right = data.size - 1
        var middle = (left + right) / 2
        val normalizedQuery = query.normalize().lowercase()
        var i: Int

        while (left <= right) {
            i = reduce(data[middle].cityName, query)

            if (data[middle].cityName.substring(0, i).lowercase() == normalizedQuery)
                return getRangeOf(data, normalizedQuery, left, right, middle)


            if (normalizedQuery > data[middle].cityName.substring(0, i).lowercase())
                left = middle + 1
            else if (normalizedQuery < data[middle].cityName.substring(0, i).lowercase())
                right = middle - 1


            middle = (left + right) / 2
        }

        return emptyList()
    }


    /**
     * Ge the range of the similar item matched with input string
     */
    private fun getRangeOf(
        data: List<City>,
        query: String,
        left: Int,
        right: Int,
        middle: Int
    ): List<City> {

        val leftBound = getLeft(query, data, left, middle)
        val rightBound = getRight(query, data, right, middle)

        return data.subList(leftBound, rightBound + 1)
    }

    /**
     * Get right bound of the range
     */
    private fun getRight(query: String, data: List<City>, rightIndex: Int, mainMiddle: Int): Int {


        var rightBound = rightIndex
        var partialLeftBound = mainMiddle + 1
        var middle = (partialLeftBound + rightBound) / 2
        var i: Int

        while (partialLeftBound <= rightBound) {
            i = reduce(data[middle].cityName, query)

            if (data[middle].cityName.substring(0, i).lowercase() > query)
                rightBound = middle - 1
            else
                partialLeftBound = middle + 1

            middle = (partialLeftBound + rightBound) / 2
        }
        return rightBound
    }

    /**
     * Get left bound of the range
     */
    private fun getLeft(
        query: String,
        data: List<City>,
        leftIndex: Int,
        mainMiddle: Int
    ): Int {

        var leftBound = leftIndex
        var partialRightBound = mainMiddle - 1
        var middle = (leftBound + partialRightBound) / 2
        var i: Int

        while (leftBound <= partialRightBound) {
            i = reduce(data[middle].cityName, query)

            if (data[middle].cityName.substring(0, i).lowercase() < query)
                leftBound = middle + 1
            else
                partialRightBound = middle - 1

            middle = (leftBound + partialRightBound) / 2
        }
        return leftBound
    }

    /**
     * Reduce the input string if greater than the one in the list
     */
    private fun reduce(first: String, second: String): Int {
        return if (first.length < second.length) first.length else second.length
    }

}
