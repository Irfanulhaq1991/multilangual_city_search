package com.example.citysearch.data

import com.example.citysearch.City
import com.example.citysearch.domain.IMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val CITY_CACHE_KEY = "citycachekey"

class CitiesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val appLruCache: IAppCache<String, List<City>>,
    private val mapper: IMapper<List<CityDto>, List<City>>
) {

    // the negative page and pageSize is the indicator to not page the data and return the complete list
    suspend fun fetchCities(page: Int = -1, pageSize: Int = -1) = withContext(Dispatchers.IO) {
        try {
            if (appLruCache.isEmpty())
                remoteDataSource.fetchCities()
                    .map {
                        mapper.map(it)
                            .also { cities -> appLruCache[CITY_CACHE_KEY] = cities }
                    }.map { pageTheData(it, page, pageSize) }
            else
                Result.success(pageTheData(appLruCache[CITY_CACHE_KEY], page, pageSize))
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }


    // Paging the data if requested
    private fun pageTheData(data: List<City>, page: Int, pageSize: Int): List<City> {
        val data2 = data
        val data3 = data.subList(page, page + pageSize)
        return if (pageSize < 0 || data.isEmpty())  // either no paging is requested or data is empty
            data
        else if (page + pageSize >= data.size)      // the paging upper bound
            data.subList(page, data.size)
        else                                        // paging fall between the lower and upper bound
            data.subList(page, page + pageSize)

    }
}

