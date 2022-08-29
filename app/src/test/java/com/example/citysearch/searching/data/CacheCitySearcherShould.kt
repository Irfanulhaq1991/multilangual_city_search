package com.example.citysearch.searching.data

import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.CitySearcher
import com.example.citysearch.searching.IAppCache
import com.example.citysearch.searching.ICitySearcher
import java.util.*

class CacheCitySearcherShould : CitySearcherContractTest() {

    override fun with(data: List<City>): ICitySearcher {
        return CitySearcher(
            object : IAppCache<String, List<City>> {
                override fun get(key: String): List<City> {
                    return data
                }

                override fun set(key: String, data: List<City>) {

                }

                override fun isEmpty(): Boolean {
                    return false
                }

                override fun isFull(): Boolean {
                    return false
                }

            }
        )
    }

    override fun withException(e: Exception): ICitySearcher {
        return CitySearcher(
            object : IAppCache<String, List<City>> {
                override fun get(key: String): List<City> {
                    throw e
                }

                override fun set(key: String, data: List<City>) {

                }

                override fun isEmpty(): Boolean {
                    return false
                }

                override fun isFull(): Boolean {
                    return false
                }

            }
        )
    }

    override fun errorMessage(): String {
        return "Error: Cache Corrupted"
    }
}