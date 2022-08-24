package com.example.citysearch.data

import com.example.citysearch.domain.City

interface ICitiesRepository {
    suspend fun fetchCities():Result<List<City>>
}