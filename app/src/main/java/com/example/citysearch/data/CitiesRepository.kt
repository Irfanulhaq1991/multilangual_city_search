package com.example.citysearch.data

import com.example.citysearch.domain.City
import com.example.citysearch.domain.IMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CitiesRepository(
    private val remoteDataSource: ICitiesDataSource,
    private val mapper: IMapper<List<CityDto>, List<City>>
) : ICitiesRepository {

    override suspend fun fetchCities() =  withContext(Dispatchers.IO) {
        remoteDataSource
            .fetchCities()
            .map { mapper.map(it) }
    }
}

