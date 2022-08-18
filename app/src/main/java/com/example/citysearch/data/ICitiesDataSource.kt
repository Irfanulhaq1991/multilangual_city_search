package com.example.citysearch.data

interface ICitiesDataSource {
    fun fetchCities(): Result<List<CityDto>>
}
