package com.example.citysearch.data

import retrofit2.Response
import retrofit2.http.GET

interface ICitiesRemoteApi {

    @GET("/")
   suspend fun fetchCities(): Response<List<CityDto>>

}
