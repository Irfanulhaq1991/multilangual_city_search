package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.TestDataProvider
import com.example.citysearch.domain.CityMapper
import com.google.common.truth.Truth
import io.mockk.coEvery
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
        val mapper = CityMapper()
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
        val data = TestDataProvider.sort(TestDataProvider.provideDomainModels().subList(0,0))
        val expected = Result.success(data)
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS().subList(0,0)) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(result).isEqualTo(expected)
    }


    @Test
    fun returnManyCities() = runTest{

        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }
        val expected = Result.success(TestDataProvider.sort(TestDataProvider.provideDomainModels()))

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

    /*
     -  save the the mapped data in cacheDataSource
     -  return the cache data source data instead of remote data source data
     -  if no data cache data is avail the fetch remote data source data
     -  Errors procrastinated
     */

}






