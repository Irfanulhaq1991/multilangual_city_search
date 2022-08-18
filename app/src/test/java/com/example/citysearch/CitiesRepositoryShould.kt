package com.example.citysearch

import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.CityDto
import com.example.citysearch.data.RemoteDataSource
import com.example.citysearch.domain.ICityMapper
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
    fun returnNoCity() {
        val expected = Result.success(emptyList<CityDto>())
        every { remoteDataSource.fetchCities() } answers { expected }
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnOneCity() {
        every { remoteDataSource.fetchCities() } answers { Result.success(listOf(DummyDataProvider.provideDTOS()[1])) }
        val expected = Result.success(listOf(DummyDataProvider.provideDomainModels()[1]))
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities() {

        every { remoteDataSource.fetchCities() } answers { Result.success(DummyDataProvider.provideDTOS()) }
        val expected = Result.success(DummyDataProvider.provideDomainModels())
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnError() {
        val expected = "No internet"
        every { remoteDataSource.fetchCities() } answers { Result.failure(Throwable(expected)) }
        val result = citiesRepository.fetchCities()
        Truth.assertThat(isFailureWithMessage(result, expected)).isTrue()
    }

}






