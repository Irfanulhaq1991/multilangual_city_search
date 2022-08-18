package com.example.citysearch

import java.io.IOException

class CitiesRepository(private val remoteDataSource: RemoteDataSource) {


    fun fetchCities(): Result<List<String>>{
            return remoteDataSource.fetchCities()
    }
}
