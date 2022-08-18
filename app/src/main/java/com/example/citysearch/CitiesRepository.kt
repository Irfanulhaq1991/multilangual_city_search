package com.example.citysearch

class CitiesRepository(private val remoteDataSource: RemoteDataSource) {


    fun fetchCities():List<String> {
        return remoteDataSource.fetchCities()
    }
}
