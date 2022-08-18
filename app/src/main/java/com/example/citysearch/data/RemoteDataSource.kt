package com.example.citysearch.data

import java.io.IOException

class RemoteDataSource(private val api: ICitiesRemoteApi):ICitiesDataSource {

   override fun fetchCities(): Result<List<CityDto>> {
       return try {
           Result.success(api.fetchCities().body()!!)
       } catch (e: IOException) {
           Result.failure(Throwable("No internet"))
       }

    }

}
