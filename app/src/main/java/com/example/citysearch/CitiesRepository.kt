package com.example.citysearch

import java.io.IOException

class CitiesRepository(private val remoteDataSource: RemoteDataSource) {


    fun fetchCities(): Result<List<String>>{
        return try {
            val result  = remoteDataSource.fetchCities()
            Result.success(result)
        }catch (e:IOException){
            Result.failure(Throwable("No internet"))
        }
    }
}
