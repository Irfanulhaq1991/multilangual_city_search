package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.DummyDataProvider
import com.google.common.truth.Truth
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class RemoteDataSourceShould : CItiesDataSourceContactTests() {


    @Test
    fun returnCouldProcessRequestError(){
        val remoteDataSource = withUnSuccessful(404,"Not found")
        val expect = "Could not process your Request"
        val actual = remoteDataSource.fetchCities()
        Truth.assertThat(isFailureWithMessage(actual,expect)).isTrue()
    }


     override fun withNoData() = RemoteDataSource(object : ICitiesRemoteApi {
        override fun fetchCities(): Response<List<CityDto>> {
            return Response.success(emptyList())
        }
    })

    // If later decided to handle each http error code
    private fun withUnSuccessful(errorCode:Int,message:String) = RemoteDataSource(object : ICitiesRemoteApi {
        override fun fetchCities(): Response<List<CityDto>> {
            return Response.error(errorCode, message.toResponseBody())
        }
    })

    override fun withData(cities: List<CityDto>) = RemoteDataSource(object : ICitiesRemoteApi {
        override fun fetchCities(): Response<List<CityDto>> {
            return Response.success(cities)
        }
    })


    override fun withException(e:Exception) = RemoteDataSource(
        object : ICitiesRemoteApi {
            override fun fetchCities(): Response<List<CityDto>> {
                throw e
            }

        }
    )


}