package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.DummyDataProvider
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceShould : BaseTest() {
    
    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun fetchNoCity() {
        val remoteDataSource = RemoteDataSource(object : ICitiesRemoteApi{
            override fun fetchCities(): Response<List<CityDto>> {
                return Response.success(emptyList())
            }
        })
        Truth.assertThat(remoteDataSource.fetchCities()).isEqualTo(Result.success(emptyList<CityDto>()))
    }

    @Test
    fun fetchOneOrManyCities(){
        val remoteDataSource = RemoteDataSource(object : ICitiesRemoteApi{
            override fun fetchCities(): Response<List<CityDto>> {
               return Response.success(listOf(DummyDataProvider.provideDTOS()[0]))
            }
        })


        Truth.assertThat(remoteDataSource.fetchCities()).isEqualTo(Result.success(emptyList<CityDto>()))
    }




}