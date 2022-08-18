package com.example.citysearch.data

interface ICitiesDataSource {
   suspend fun fetchCities(): Result<List<CityDto>>
}
