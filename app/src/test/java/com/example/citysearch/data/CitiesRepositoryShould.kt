package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.City
import com.example.citysearch.TestDataProvider
import com.example.citysearch.domain.CityMapper
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.lang.IllegalArgumentException


class CitiesRepositoryShould : BaseTest()  {



    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource
    @RelaxedMockK
    private lateinit var appCache: AppCache<String,List<City>>

    private lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        val mapper = CityMapper()
        citiesRepository = CitiesRepository(remoteDataSource,appCache,mapper)
    }

    @Test
    fun returnNoCity() = runTest {
        //Given
        coEvery { appCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(emptyList())}

        //When
        val result = citiesRepository.fetchCities()

        //then
        Truth.assertThat(result).isEqualTo(Result.success(emptyList<City>()))
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery { appCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS().subList(0,0)) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(result).isEqualTo(Result.success(TestDataProvider.sort(TestDataProvider.provideDomainModels().subList(0,0))))
    }


    @Test
    fun returnManyCities() = runTest{
        coEvery { appCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(TestDataProvider.provideDomainModels())))
    }

    @Test
    fun returnError() = runTest{
        coEvery { appCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.failure(Throwable("No internet")) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }

    /*
     -  save the the mapped data in cacheDataSource
     -  return the cache data source data instead of remote data source data
     -  if no data cache data is avail the fetch remote data source data
     -  Errors procrastinated
     */

    @Test
    fun putInCacheTheMappedData()= runTest{

        coEvery { appCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        citiesRepository.fetchCities()

        coVerify { remoteDataSource.fetchCities() }
        coVerify { appCache[any()] = any() }
    }

    @Test
    fun getInCachedMappedData() = runTest {
        coEvery { appCache.isEmpty() } answers { false }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        citiesRepository.fetchCities()

        coVerify(exactly = 0) { remoteDataSource.fetchCities() }
        coVerify { appCache[any()] }
    }


    @Test
    fun getRemoteDataIfNoCacheDataIAvailable() = runTest {
        coEvery { appCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        citiesRepository.fetchCities()

        coVerify { remoteDataSource.fetchCities() }
        coVerify(exactly = 0){ appCache[any()] }
    }

    @Test
    fun returnErrorWhenPuttingInCache() = runTest{
        coEvery { appCache.isEmpty() } answers { true }
        coEvery {  appCache[any()] = any() } throws IllegalArgumentException("Unknown Error occurred")
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }


        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }

    @Test
    fun returnErrorWhenGettingInCache() = runTest{
        coEvery { appCache.isEmpty() } answers { false }
        coEvery {  appCache[any()]} throws NullPointerException("Unknown Error occurred")
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }


        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }


}






