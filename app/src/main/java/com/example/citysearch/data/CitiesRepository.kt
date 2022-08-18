package com.example.citysearch.data

import com.example.citysearch.City
import com.example.citysearch.Coordinates

class CitiesRepository(private val remoteDataSource: RemoteDataSource) {


    fun fetchCities(): Result<List<City>>{
            return remoteDataSource.fetchCities().map { map(it) }
    }

    private fun map(cities:List<String>):List<City>{
        return cities.map { City(0,it,"Uk", Coordinates(1.0,1.0)) }
    }
}
