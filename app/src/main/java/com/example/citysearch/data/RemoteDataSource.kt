package com.example.citysearch.data

class RemoteDataSource(fakeCitiesRemoteApi: CitiesRemoteApi) {

    fun fetchCities(): Result<List<CityDto>> {
       return Result.success(emptyList<CityDto>())
    }

}
