package com.example.citysearch.data

class RemoteDataSource {

    fun fetchCities(): Result<List<CityDto>> {
       return Result.success(emptyList<CityDto>())
    }

}
