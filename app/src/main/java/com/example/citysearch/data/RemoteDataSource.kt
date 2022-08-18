package com.example.citysearch.data

import java.io.IOException

class RemoteDataSource(private val api: ICitiesRemoteApi) {

    fun fetchCities(): Result<List<CityDto>> {
        try {
            return Result.success(api.fetchCities().body()!!)
        } catch (e: IOException) {
            return Result.failure(Throwable("No internet"))
        }

    }

}
