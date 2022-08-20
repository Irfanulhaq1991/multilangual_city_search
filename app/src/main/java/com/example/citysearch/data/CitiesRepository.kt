package com.example.citysearch.data

import com.example.citysearch.City
import com.example.citysearch.domain.IMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val CITY_CACHE_KEY = "citycachekey"

class CitiesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val appCache: AppCache<String, List<City>>,
    private val mapper: IMapper<List<CityDto>, List<City>>
) {

    suspend fun fetchCities() = withContext(Dispatchers.IO) {
        remoteDataSource.fetchCities()
            .map {
                mapper.map(it)
                    .also { cities -> appCache.put(CITY_CACHE_KEY, cities) }
            }
    }
}

