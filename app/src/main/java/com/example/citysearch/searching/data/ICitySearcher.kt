package com.example.citysearch.searching.data

import com.example.citysearch.fetching.domain.City

interface ICitySearcher {
    fun searchCity(query: String): Result<List<City>>
}