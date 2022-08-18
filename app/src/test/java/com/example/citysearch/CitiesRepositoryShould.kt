package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.DummyDataProvider
import com.example.citysearch.domain.ICityMapper
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class CitiesRepositoryShould : BaseTest()  {

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        val mapper = ICityMapper()
        citiesRepository = CitiesRepository(remoteDataSource,mapper)
    }

    @Test
    fun returnNoCity() = runTest {
        val expected = Result.success(emptyList<CityDto>())
        coEvery {  remoteDataSource.fetchCities() } answers { expected }
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(listOf(DummyDataProvider.provideDTOS()[1])) }
        val expected = Result.success(listOf(DummyDataProvider.provideDomainModels()[1]))
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities() = runTest{

        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(DummyDataProvider.provideDTOS()) }
        val expected = Result.success(DummyDataProvider.provideDomainModels())
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnError() = runTest{
        val expected = "No internet"
        coEvery {  remoteDataSource.fetchCities() } answers { Result.failure(Throwable(expected)) }
        val result = citiesRepository.fetchCities()
        Truth.assertThat(isFailureWithMessage(result, expected)).isTrue()
    }

}






