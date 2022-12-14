package com.example.citysearch.fetching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.TestDataProviderProvider
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
        val remoteDataSource = withData(listOf(TestDataProviderProvider.provideDtoFromBeginning()[0]))
        val expected = listOf(TestDataProviderProvider.provideDtoFromBeginning()[0])

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun fetchManyCities() = runTest {
        val remoteDataSource = withData(TestDataProviderProvider.provideDtoFromBeginning())
        val expected = Result.success(TestDataProviderProvider.provideDtoFromBeginning())

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnInternetError() = runTest {
        val remoteDataSource = withException(IOException())

        val expectedErrorMessage = errorMessage()

        val actual = remoteDataSource.fetchCities()

        Truth.assertThat(isFailureWithMessage(actual, expectedErrorMessage)).isTrue()
    }

    abstract fun errorMessage(): String


    abstract fun withException(e: Exception): ICitiesDataSource

    abstract fun withNoData(): ICitiesDataSource

    abstract fun withData(cities: List<CityDto>): ICitiesDataSource
}


