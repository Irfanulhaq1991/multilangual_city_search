package com.example.citysearch

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CitiesRepositoryShould : BaseTest(){

    @MockK
   private lateinit var remoteDataSource : RemoteDataSource
   private lateinit var citiesRepository : CitiesRepository

   @Before
    override fun setUp() {
        super.setUp()
       citiesRepository = CitiesRepository(remoteDataSource)
    }
    @Test
    fun returnNoCity(){
        val expected = Result.success(emptyList<String>())
        every { remoteDataSource.fetchCities() } answers { expected}
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnOneCity(){
        val expected = Result.success(listOf("London"))
        every { remoteDataSource.fetchCities() } answers { expected }
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities(){
        val expected = Result.success(listOf("London","Yorkshire"))
        every { remoteDataSource.fetchCities() } answers { expected}
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnError(){
        val expected = "No internet"
        every { remoteDataSource.fetchCities() } answers { Result.failure(Throwable(expected)) }
        val citiesRepository = CitiesRepository(remoteDataSource)
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(isFailureWithMessage(result,"No internet")).isTrue()
    }
}