package com.example.citysearch.data

import com.example.citysearch.data.cache.IAppCache
import com.example.citysearch.domain.City
import com.example.citysearch.domain.IMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val CITY_CACHE_KEY = "citycachekey"

class CitiesRepository(
    private val remoteDataSource: ICitiesDataSource,
    private val cache: IAppCache<String, List<City>>,
    private val mapper: IMapper<List<CityDto>, List<City>>
) {

    // the negative page and pageSize is the indicator to not page the data and return the complete list
    suspend fun fetchCities(page: Int = -1, pageSize: Int = -1) = withContext(Dispatchers.IO) {
        try {
            if (cache.isEmpty())
                remoteDataSource.fetchCities()
                    .map {
                        mapper.map(it)
                            .also { cities -> cache[CITY_CACHE_KEY] = cities }
                    }.map { pageTheData(it, page, pageSize) }
            else
                Result.success(pageTheData(cache[CITY_CACHE_KEY], page, pageSize))
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }


    // Paging the data if requested
    private fun pageTheData(data: List<City>, page: Int, pageSize: Int): Pair<List<City>, Int> {
        val pageData: List<City> =
            if (pageSize < 0 || data.isEmpty())  // either no paging is requested or data is empty
                data
            else if (page + pageSize >= data.size)      // the paging upper bound
                data.subList(page, data.size)
            else                                        // paging fall between the lower and upper bound
                data.subList(page, page + pageSize)
        return Pair(pageData,data.size)
    }
}

