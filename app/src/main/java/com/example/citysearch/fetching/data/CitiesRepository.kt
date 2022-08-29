package com.example.citysearch.fetching.data

import com.example.citysearch.common.CITY_LIST_KEY
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.mapper.IMapper
import com.example.citysearch.searching.data.IAppCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CitiesRepository(
    private val remoteDataSource: ICitiesDataSource,
    private val cache: IAppCache<String, List<City>>,
    private val mapper: IMapper<List<CityDto>, List<City>>
) : ICitiesRepository {

    override suspend fun fetchCities() = withContext(Dispatchers.IO) {
        if (!cache.isEmpty()) Result.success(cache[CITY_LIST_KEY])
        else
            remoteDataSource
                .fetchCities()
                .map {
                    val cities =  mapper.map(it)
                    cache[CITY_LIST_KEY] = cities
                    cities
                }

    }
}

