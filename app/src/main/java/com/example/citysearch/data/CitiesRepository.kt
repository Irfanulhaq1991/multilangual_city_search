package com.example.citysearch.data

import com.example.citysearch.City
import com.example.citysearch.domain.IMapper

class CitiesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: IMapper<List<CityDto>,List<City>>
) {
    fun fetchCities(): Result<List<City>>{
            return remoteDataSource.fetchCities()
                .map { mapper.map(it) }
    }
}

