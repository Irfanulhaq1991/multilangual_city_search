package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.DummyDataProvider
import com.google.common.truth.Truth
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class RemoteDataSourceShould:BaseTest(){


    @Test
    fun fetchNoCity() {
        val remoteDataSource = withNoData()
        Truth.assertThat(remoteDataSource.fetchCities()).isEqualTo(Result.success(emptyList<CityDto>()))
    }



    @Test
    fun fetchOneCities(){
        val remoteDataSource = withData(listOf(DummyDataProvider.provideDTOS()[0]))
        Truth.assertThat(remoteDataSource.fetchCities()).isEqualTo(Result.success(listOf(DummyDataProvider.provideDTOS()[0])))
    }

    @Test
    fun fetchManyCities(){
        val remoteDataSource = withData(DummyDataProvider.provideDTOS())

        Truth.assertThat(remoteDataSource.fetchCities()).isEqualTo(Result.success(DummyDataProvider.provideDTOS()))
    }

    @Test
    fun returnInternetError(){
        Truth.assertThat(isFailureWithMessage(RemoteDataSource(
            object :ICitiesRemoteApi{
                override fun fetchCities(): Response<List<CityDto>> {
                    throw IOException()
                }

            }



        ).fetchCities(),"No internet")).isTrue()
    }


    private fun withNoData() = RemoteDataSource(object : ICitiesRemoteApi {
        override fun fetchCities(): Response<List<CityDto>> {
            return Response.success(emptyList())
        }
    })


    private fun withData(cities: List<CityDto>) = RemoteDataSource(object : ICitiesRemoteApi {
        override fun fetchCities(): Response<List<CityDto>> {
            return Response.success(cities)
        }
    })

}