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
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException


class CitiesRepositoryShould : BaseTest() {


    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource

    @RelaxedMockK
    private lateinit var appLruCache: AppLruCache<String, List<City>>

    private lateinit var citiesRepository: CitiesRepository

    private var testDto = emptyList<CityDto>()
    private var testDomain = emptyList<City>()
    private var totalCount = 0

    @Before
    override fun setUp() {
        super.setUp()
        val mapper = CityMapper()
        citiesRepository = CitiesRepository(remoteDataSource, appLruCache, mapper)

        testDto = TestDataProvider.provideDTOS()
        testDomain = TestDataProvider.provideDomainModels()
        totalCount = testDomain.size
    }

    @After

    fun tearDown() {
        testDto = emptyList()
        testDomain = emptyList()
        totalCount = 0
    }

    @Test
    fun returnNoCity() = runTest {
        //Given
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(emptyList()) }


        //When
        val result = citiesRepository.fetchCities(1, 50)

        //then
        Truth.assertThat(result).isEqualTo(Result.success(emptyList<City>()))
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers {
            Result.success(
                TestDataProvider.sortDto(
                    testDto
                ).subList(0, 1)
            )
        }

        val result = citiesRepository.fetchCities(0, 50)

        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(testDomain.subList(0, 1))))
    }


    @Test
    fun returnManyCities() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val result = citiesRepository.fetchCities(0, 50)

        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(testDomain).subList(0, 50)))
    }


    // // corner case page+pagSize >= end Index
    @Test
    fun returnCitiesPageIsGreaterThanEnd() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val pageSize = 5
        val startIndex = totalCount - pageSize
        val endIndex = totalCount

        val result = citiesRepository.fetchCities(startIndex, pageSize)

        Truth.assertThat(result)
            .isEqualTo(
                Result.success(
                    TestDataProvider.sort(
                        testDomain
                            .subList(startIndex, endIndex)
                    )
                )
            )
    }

    // corner case page+pagSize == end Index
    @Test
    fun returnCitiesPageIsEqualEnd() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val pageSize = 5
        val startIndex = totalCount - pageSize
        val endIndex = totalCount

        val result = citiesRepository.fetchCities(startIndex, pageSize)

        Truth.assertThat(result)
            .isEqualTo(
                Result.success(
                    TestDataProvider.sort(
                        testDomain
                            .subList(startIndex, endIndex)
                    )
                )
            )
    }

    // corner case page+pagSize one less than end Index
    @Test
    fun returnCitiesPagerSizeIsLessThanEnd() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val pageSize = 5
        val startIndex = totalCount - pageSize
        val endIndex = totalCount - 1

        val result = citiesRepository.fetchCities(startIndex, pageSize - 1)

        Truth.assertThat(result)
            .isEqualTo(
                Result.success(
                    TestDataProvider.sort(
                        testDomain
                            .subList(startIndex, endIndex)
                    )
                )
            )
    }


    // corner case page+pagSize == 0
    @Test
    fun returnCitiesPagerSizeIsZero() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val pageSize = 0
        val startIndex = 0
        val endIndex = 0

        val result = citiesRepository.fetchCities(0, pageSize)

        Truth.assertThat(result)
            .isEqualTo(
                Result.success(
                    TestDataProvider.sort(
                        testDomain
                            .subList(startIndex, endIndex)
                    )
                )
            )
    }

    // corner case page+pagSize == 1
    @Test
    fun returnCitiesPagerSizeIsOne() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val pageSize = 1
        val startIndex = 0
        val endIndex = 1

        val result = citiesRepository.fetchCities(0, pageSize)

        Truth.assertThat(result)
            .isEqualTo(
                Result.success(
                    TestDataProvider.sort(
                        testDomain
                            .subList(startIndex, endIndex)
                    )
                )
            )
    }


    @Test
    fun returnAllCities() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(result).isEqualTo(Result.success(TestDataProvider.sort(testDomain)))
    }

    @Test
    fun returnError() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.failure(Throwable("No internet")) }

        val result = citiesRepository.fetchCities(0, 50)

        Truth.assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }


    @Test
    fun putInCacheTheMappedData() = runTest {

        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val result = citiesRepository.fetchCities(0, 50)

        coVerify { remoteDataSource.fetchCities() }
        coVerify { appLruCache[any()] = any() }
        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(testDomain).subList(0, 50)))
    }

    @Test
    fun getInCachedMappedData() = runTest {
        coEvery { appLruCache.isEmpty() } answers { false }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }
        coEvery { appLruCache[any()] } answers { TestDataProvider.provideDomainModels() }

        val result = citiesRepository.fetchCities(0, 50)

        coVerify(exactly = 0) { remoteDataSource.fetchCities() }
        coVerify { appLruCache[any()] }
        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(testDomain).subList(0, 50)))
    }


    @Test
    fun getRemoteDataIfNoCacheDataIAvailable() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }

        val result = citiesRepository.fetchCities(0, 50)

        coVerify { remoteDataSource.fetchCities() }
        coVerify(exactly = 0) { appLruCache[any()] }
        Truth.assertThat(result)
            .isEqualTo(Result.success(TestDataProvider.sort(testDomain).subList(0, 50)))
    }

    @Test
    fun returnErrorWhenPuttingInCache() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {
            appLruCache[any()] = any()
        } throws IllegalArgumentException("Unknown Error occurred")
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }


        val result = citiesRepository.fetchCities(0, 50)

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }

    @Test
    fun returnErrorWhenGettingInCache() = runTest {
        coEvery { appLruCache.isEmpty() } answers { false }
        coEvery { appLruCache[any()] } throws NullPointerException("Unknown Error occurred")
        coEvery { remoteDataSource.fetchCities() } answers { Result.success(testDto) }


        val result = citiesRepository.fetchCities(1, 50)

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }


}






