package com.example.citysearch

import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.CityDto
import com.example.citysearch.data.CoordinatesDto
import com.example.citysearch.data.RemoteDataSource
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class CitiesRepositoryShould : BaseTest() {

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        citiesRepository = CitiesRepository(remoteDataSource)
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
        every { remoteDataSource.fetchCities() } answers { Result.success(listOf(provideDTOS()[1])) }
        val expected = Result.success(listOf(provideDomainModels()[1]))
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities() {

        every { remoteDataSource.fetchCities() } answers { Result.success(provideDTOS()) }
        val expected = Result.success(provideDomainModels())
        val result = citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnError() {
        val expected = "No internet"
        every { remoteDataSource.fetchCities() } answers { Result.failure(Throwable(expected)) }
        val citiesRepository = CitiesRepository(remoteDataSource)
        val result = citiesRepository.fetchCities()
        Truth.assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }





    
    private fun provideDTOS(): List<CityDto> {
        return listOf(

            CityDto(
                "UK", "London", 12345,
                CoordinatesDto(1.0, 1.0)
            ),
            CityDto(
                "UK", "Yorkshire", 123456,
                CoordinatesDto(2.0, 2.0)
            ),

            )
    }

    fun provideDomainModels(): List<City> {
        return listOf(
            City(12345,"London","UK",
                Coordinates(1.0, 1.0)
            ),
            City(
                123456,"Yorkshire","UK",
                Coordinates(2.0, 2.0)
            ),

            )
    }
}






