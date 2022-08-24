package com.example.citysearch.fetching.data

interface ICitiesDataSource {
   suspend fun fetchCities(): Result<List<CityDto>>
}
