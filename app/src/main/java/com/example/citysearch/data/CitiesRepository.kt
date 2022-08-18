package com.example.citysearch.data

import com.example.citysearch.City
import com.example.citysearch.domain.IMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: IMapper<List<CityDto>, List<City>>
) {
    suspend fun fetchCities() = withContext(Dispatchers.IO) {
        remoteDataSource.fetchCities()
            .map { mapper.map(it) }
    }
}

