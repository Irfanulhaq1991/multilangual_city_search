package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.data.cache.AppLruCache
import com.example.citysearch.data.localfile.FileDataSource
import com.example.citysearch.domain.City
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
    private lateinit var dataSource: FileDataSource

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
        citiesRepository = CitiesRepository(dataSource, appLruCache, mapper)

        testDto = TestDataProviderProvider.provideDTOS()
        testDomain = TestDataProviderProvider.provideDomainModels()
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
        coEvery { dataSource.fetchCities() } answers { Result.success(emptyList()) }
        val expected = Pair(emptyList<City>(), 0)


        //When
        val result = citiesRepository.fetchCities(1, 50)

        //then
        Truth
            .assertThat(result)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {
            dataSource.fetchCities()
        } answers { Result.success(TestDataProviderProvider.sortDto(testDto).subList(0, 1)) }
        val expected =
            Pair(TestDataProviderProvider.sortDomainModels(testDomain.subList(0, 1)), 1)

        val actual = citiesRepository.fetchCities(0, 50)

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }


    @Test
    fun returnManyCities() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected =
            Pair(TestDataProviderProvider.sortDomainModels(testDomain.subList(0, 50)), totalCount)

        val actual = citiesRepository.fetchCities(0, 50)

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }


    // // corner case page+pagSize >= end Index
    @Test
    fun returnCitiesPageIsGreaterThanEnd() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val pageSize = 5
        val startIndex = totalCount - pageSize
        val endIndex = totalCount
        val expected = Pair(
            TestDataProviderProvider.sortDomainModels(testDomain.subList(startIndex, endIndex)),
            totalCount
        )


        val result = citiesRepository.fetchCities(startIndex, pageSize)

        Truth
            .assertThat(result)
            .isEqualTo(Result.success(expected))
    }

    // corner case page+pagSize == end Index
    @Test
    fun returnCitiesPageIsEqualEnd() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val pageSize = 5
        val startIndex = totalCount - pageSize
        val endIndex = totalCount
        val expected = Pair(
            TestDataProviderProvider.sortDomainModels(testDomain.subList(startIndex, endIndex)),
            totalCount
        )


        val actual = citiesRepository.fetchCities(startIndex, pageSize)

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    // corner case page+pagSize one less than end Index
    @Test
    fun returnCitiesPagerSizeIsLessThanEnd() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val pageSize = 5
        val startIndex = totalCount - pageSize
        val endIndex = totalCount - 1
        val expected = Pair(
            TestDataProviderProvider.sortDomainModels(testDomain.subList(startIndex, endIndex)),
            totalCount
        )

        val actual = citiesRepository.fetchCities(startIndex, pageSize - 1)

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }


    // corner case page+pagSize == 0
    @Test
    fun returnCitiesPagerSizeIsZero() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val pageSize = 0
        val startIndex = 0
        val endIndex = 0
        val expected = Pair(
            TestDataProviderProvider.sortDomainModels(testDomain.subList(startIndex, endIndex)),
            totalCount
        )


        val actual = citiesRepository.fetchCities(0, pageSize)

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    // corner case page+pagSize == 1
    @Test
    fun returnCitiesPagerSizeIsOne() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val pageSize = 1
        val startIndex = 0
        val endIndex = 1
        val expected = Pair(
            TestDataProviderProvider.sortDomainModels(testDomain.subList(startIndex, endIndex)),
            totalCount
        )


        val actual = citiesRepository.fetchCities(0, pageSize)

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }


    @Test
    fun returnAllCities() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected =
            Pair(TestDataProviderProvider.sortDomainModels(testDomain), totalCount)

        val actual = citiesRepository.fetchCities()

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnError() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.failure(Throwable("No internet")) }

        val result = citiesRepository.fetchCities(0, 50)

        Truth.assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }


    @Test
    fun putInCacheTheMappedData() = runTest {

        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected =
            Pair(TestDataProviderProvider.sortDomainModels(testDomain.subList(0, 50)), totalCount)

        val result = citiesRepository.fetchCities(0, 50)

        coVerify { dataSource.fetchCities() }
        coVerify { appLruCache[any()] = any() }
        Truth
            .assertThat(result)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun getInCachedMappedData() = runTest {
        coEvery { appLruCache.isEmpty() } answers { false }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        coEvery { appLruCache[any()] } answers { TestDataProviderProvider.provideDomainModels() }
        val expected =
            Pair(TestDataProviderProvider.sortDomainModels(testDomain.subList(0, 50)), totalCount)

        val result = citiesRepository.fetchCities(0, 50)

        coVerify(exactly = 0) { dataSource.fetchCities() }
        coVerify { appLruCache[any()] }
        Truth
            .assertThat(result)
            .isEqualTo(Result.success(expected))
    }


    @Test
    fun getRemoteDataIfNoCacheDataIAvailable() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected =
            Pair(TestDataProviderProvider.sortDomainModels(testDomain.subList(0, 50)), totalCount)

        val result = citiesRepository.fetchCities(0, 50)

        coVerify { dataSource.fetchCities() }
        coVerify(exactly = 0) { appLruCache[any()] }
        Truth
            .assertThat(result)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnErrorWhenPuttingInCache() = runTest {
        coEvery { appLruCache.isEmpty() } answers { true }
        coEvery {
            appLruCache[any()] = any()
        } throws IllegalArgumentException("Unknown Error occurred")
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }


        val result = citiesRepository.fetchCities(0, 50)

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }

    @Test
    fun returnErrorWhenGettingInCache() = runTest {
        coEvery { appLruCache.isEmpty() } answers { false }
        coEvery { appLruCache[any()] } throws NullPointerException("Unknown Error occurred")
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }


        val result = citiesRepository.fetchCities(1, 50)

        Truth.assertThat(isFailureWithMessage(result, "Unknown Error occurred"))
            .isTrue()
    }


}






