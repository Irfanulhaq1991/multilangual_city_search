package com.example.citysearch

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CitiesRepositoryShould : BaseTest(){
   // At the moment, No idea of the complete logic and design of the citiesRepository
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
        every { remoteDataSource.fetchCities() } answers { emptyList()}
        val expected = Result.success(emptyList<String>())
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnOneCity(){
        every { remoteDataSource.fetchCities() } answers { listOf("London") }
        val expected = Result.success(listOf("London"))
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities(){
        every { remoteDataSource.fetchCities() } answers { listOf("London","Yorkshire") }
        val expected = Result.success(listOf("London","Yorkshire"))
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun returnError(){
        every { remoteDataSource.fetchCities() } throws IOException("No internet")
        val citiesRepository = CitiesRepository(remoteDataSource)
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(isFailureWithMessage(result,"No internet")).isTrue()
    }


}