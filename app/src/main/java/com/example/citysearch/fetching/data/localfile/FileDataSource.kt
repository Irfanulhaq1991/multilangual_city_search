package com.example.citysearch.fetching.data.localfile

import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.ICitiesDataSource

class FileDataSource(private val jsonSourceProvider: JsonDataProvider): ICitiesDataSource {
    override suspend fun fetchCities(): Result<List<CityDto>> {
        return try {
            val allCities = jsonSourceProvider.getJsonCitiesFromAssets()
            Result.success(jsonSourceProvider.deSerializeAllCitiesJson(allCities!!))
        }catch (e:Exception){
            Result.failure( Throwable("Could not get data"))
        }
    }
}