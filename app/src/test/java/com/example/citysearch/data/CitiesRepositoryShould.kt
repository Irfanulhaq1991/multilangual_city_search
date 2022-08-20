package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.City
import com.example.citysearch.TestDataProvider
import com.example.citysearch.domain.CityMapper
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException


class CitiesRepositoryShould : BaseTest()  {



    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource
    @RelaxedMockK
    private lateinit var appLruCache: AppLruCache<String,List<City>>

    private lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        val mapper = CityMapper()
        citiesRepository = CitiesRepository(remoteDataSource,appLruCache,mapper)
    }

    @Test
    fun returnNoCity() = runTest {
        //Given
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(emptyList())}

        
        //When
        val result = citiesRepository.fetchCities()

        //then
        Truth.assertThat(result).isEqualTo(Result.success(emptyList<City>()))
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS().subList(0,0)) }

        /*
       - provided a page and item count the repository should the total cities which less than page

       */



        val result = citiesRepository.fetchCities()

        Truth.assertThat(result).isEqualTo(Result.success(TestDataProvider.sort(TestDataProvider.provideDomainModels().subList(0,0))))
    }


    @Test
    fun returnManyCities() = runTest{
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        val result = citiesRepository.fetchCities()

        /*
         - provided a page and item count the repository should return a page of size of item count

         */





        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(TestDataProvider.provideDomainModels())))
    }

    @Test
    fun returnError() = runTest{
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.failure(Throwable("No internet")) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }


    @Test
    fun putInCacheTheMappedData()= runTest{

        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        citiesRepository.fetchCities()

        coVerify { remoteDataSource.fetchCities() }
        coVerify { appLruCache[any()] = any() }
    }

    @Test
    fun getInCachedMappedData() = runTest {
        coEvery { appLruCache.isEmpty() } answers { false }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        citiesRepository.fetchCities()

        coVerify(exactly = 0) { remoteDataSource.fetchCities() }
        coVerify { appLruCache[any()] }
    }


    @Test
    fun getRemoteDataIfNoCacheDataIAvailable() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }

        citiesRepository.fetchCities()

        coVerify { remoteDataSource.fetchCities() }
        coVerify(exactly = 0){ appLruCache[any()] }
    }

    @Test
    fun returnErrorWhenPuttingInCache() = runTest{
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {  appLruCache[any()] = any() } throws IllegalArgumentException("Unknown Error occurred")
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }


        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }

    @Test
    fun returnErrorWhenGettingInCache() = runTest{
        coEvery { appLruCache.isEmpty() } answers { false }
        coEvery {  appLruCache[any()]} throws NullPointerException("Unknown Error occurred")
        coEvery {  remoteDataSource.fetchCities() } answers { Result.success(TestDataProvider.provideDTOS()) }


        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }


}






