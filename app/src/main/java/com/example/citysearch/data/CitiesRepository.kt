package com.example.citysearch.data

import com.example.citysearch.City
import com.example.citysearch.Coordinates

class CitiesRepository(private val remoteDataSource: RemoteDataSource) {


    fun fetchCities(): Result<List<City>>{
            return remoteDataSource.fetchCities().map { map(it) }
    }

    private fun map(cities: List<CityDto>):List<City>{
        return cities.map { City(it._id,it.name,it.country,Coordinates(it.coordinates.lon,it.coordinates.lat)) }
    }
}
