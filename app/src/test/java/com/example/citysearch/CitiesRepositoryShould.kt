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
   lateinit var remoteDataSource : RemoteDataSource

   @Before
    override fun setUp() {
        super.setUp()
    }
    @Test
    fun returnNoCity(){
        every { remoteDataSource.fetchCities() } answers { emptyList()}
        val citiesRepository = CitiesRepository(remoteDataSource)
        Truth.assertThat(citiesRepository.fetchCities()).isEqualTo(emptyList<String>())
    }

    @Test
    fun returnOneCity(){
        every { remoteDataSource.fetchCities() } answers { listOf("London") }
        val citiesRepository = CitiesRepository(remoteDataSource)
        Truth.assertThat(citiesRepository.fetchCities()).isEqualTo(listOf("London"))
    }


    @Test
    fun returnManyCities(){
        every { remoteDataSource.fetchCities() } answers { listOf("London","Yorkshire") }
        val citiesRepository = CitiesRepository(remoteDataSource)
        Truth.assertThat(citiesRepository.fetchCities()).isEqualTo(listOf("London","Yorkshire"))
    }



    @Test
    fun returnError(){
        every { remoteDataSource.fetchCities() } throws IOException("No internet")
        val citiesRepository = CitiesRepository(remoteDataSource)
        Truth.assertThat(isFailureWithMessage(citiesRepository.fetchCities(),"No internet")).isTrue()
    }


}