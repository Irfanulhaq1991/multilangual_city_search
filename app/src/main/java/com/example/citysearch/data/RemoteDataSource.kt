package com.example.citysearch.data

import java.io.IOException

class RemoteDataSource(private val api: ICitiesRemoteApi):ICitiesDataSource {
    override suspend fun fetchCities(): Result<List<CityDto>> {
        return try {
            val response= api.fetchCities()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Throwable("Could not process your request"))
            }

        } catch (e: IOException) {
            Result.failure(Throwable("No internet"))
        }
    }

}
