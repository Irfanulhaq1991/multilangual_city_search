package com.example.citysearch.data.localfile

import com.example.citysearch.data.CityDto
import com.example.citysearch.data.ICitiesDataSource

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