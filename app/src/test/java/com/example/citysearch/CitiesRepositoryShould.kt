package com.example.citysearch

import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.RemoteDataSource
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

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
        every { remoteDataSource.fetchCities() } answers { Result.success(listOf("London"))}
        val expected = Result.success(listOf(City(0,"London","Uk", Coordinates(1.0,1.0))))
        val result =  citiesRepository.fetchCities()
        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities(){

        every { remoteDataSource.fetchCities() } answers { Result.success(listOf("London","Yorkshire"))}
        val expected = Result.success(listOf(City(0,"London","Uk", Coordinates(1.0,1.0)),
            City(1,"Yorkshire","Uk", Coordinates(2.0,2.0))),
        )
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


