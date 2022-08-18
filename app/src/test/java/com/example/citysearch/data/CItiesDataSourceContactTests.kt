package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.DummyDataProvider
import com.google.common.truth.Truth
import org.junit.Test
import retrofit2.Response
import java.io.IOException


// Contract test to verify the logics of the ICitiesDataSource subclasses e.g databaseDataSource, RemoteDataSource, CacheDataSource and etc.
abstract class ICitiesDataSourceContactTests : BaseTest() {
    @Test
    fun fetchNoCity() {
        val remoteDataSource = withNoData()
        val expected = Result.success(emptyList<CityDto>())

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun fetchOneCities() {
        val remoteDataSource = withData(listOf(DummyDataProvider.provideDTOS()[0]))
        val expected = listOf(DummyDataProvider.provideDTOS()[0])

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun fetchManyCities() {
        val remoteDataSource = withData(DummyDataProvider.provideDTOS())
        val expected = Result.success(DummyDataProvider.provideDTOS())

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnInternetError() {
        val remoteDataSource = withException(IOException())
        val expect = "No internet"

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(isFailureWithMessage(actual, expect)).isTrue()
    }


    abstract fun withException(e:Exception): ICitiesDataSource

    abstract fun withNoData(): ICitiesDataSource

    abstract fun withData(cities: List<CityDto>): ICitiesDataSource
}


