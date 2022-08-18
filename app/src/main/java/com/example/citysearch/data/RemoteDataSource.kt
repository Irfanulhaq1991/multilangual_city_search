package com.example.citysearch.data

class RemoteDataSource(private val api : ICitiesRemoteApi) {

    fun fetchCities(): Result<List<CityDto>> {
       return Result.success(api.fetchCities().body()!!)
    }

}
