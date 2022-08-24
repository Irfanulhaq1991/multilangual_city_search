package com.example.citysearch.fetching.data

import com.example.citysearch.fetching.domain.City

interface ICitiesRepository {
    suspend fun fetchCities():Result<List<City>>
}