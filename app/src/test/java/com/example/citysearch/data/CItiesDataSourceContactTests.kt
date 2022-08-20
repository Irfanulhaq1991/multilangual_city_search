package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.TestDataProvider
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException


// Contract test to verify the logics of the ICitiesDataSource subclasses e.g databaseDataSource, RemoteDataSource, CacheDataSource and etc.
abstract class ICitiesDataSourceContactTests : BaseTest() {
    @Test
    fun fetchNoCity() = runTest {
        val remoteDataSource = withNoData()
        val expected = Result.success(emptyList<CityDto>())

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun fetchOneCities() = runTest {
        val remoteDataSource = withData(listOf(TestDataProvider.provideDTOS()[0]))
        val expected = listOf(TestDataProvider.provideDTOS()[0])

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun fetchManyCities() = runTest {
        val remoteDataSource = withData(TestDataProvider.provideDTOS())
        val expected = Result.success(TestDataProvider.provideDTOS())

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnInternetError() = runTest {
        val remoteDataSource = withException(IOException())
        val expect = "No internet"

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(isFailureWithMessage(actual, expect)).isTrue()
    }


    abstract fun withException(e: Exception): ICitiesDataSource

    abstract fun withNoData(): ICitiesDataSource

    abstract fun withData(cities: List<CityDto>): ICitiesDataSource
}

