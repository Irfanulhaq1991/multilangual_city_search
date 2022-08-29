package com.example.citysearch.fetching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.TestDataProviderProvider
import com.example.citysearch.fetching.data.localfile.FileDataSource
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.CityMapper
import com.example.citysearch.searching.IAppCache
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class CitiesRepositoryShould : BaseTest() {


    @RelaxedMockK
    private lateinit var cache: IAppCache<String, List<City>>

    @RelaxedMockK
    private lateinit var dataSource: FileDataSource

    private lateinit var citiesRepository: CitiesRepository

    private var testDto = emptyList<CityDto>()
    private var testDomain = emptyList<City>()
    private var totalCount = 0

    @Before
    override fun setup() {
        super.setup()
        val mapper = CityMapper()
        citiesRepository = CitiesRepository(dataSource, cache, mapper)

        testDto = TestDataProviderProvider.provideDOSFromBeginning()
        testDomain = TestDataProviderProvider.provideDomainModelsFromBeginning()
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
        coEvery { cache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(emptyList()) }
        val expected = emptyList<City>()


        //When
        val actual = citiesRepository.fetchCities()
        coVerify(exactly = 0) { cache[any()] }

        //then
        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery { cache.isEmpty() } answers { true }

        coEvery {
            dataSource.fetchCities()
        } answers { Result.success(TestDataProviderProvider.sortDto(testDto).subList(0, 1)) }
        val expected = TestDataProviderProvider
            .sortDomainModels(testDomain.subList(0, 1))

        val actual = citiesRepository.fetchCities()

        coVerify(exactly = 0) { cache[any()] }
        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }


    @Test
    fun returnAllCities() = runTest {
        coEvery { cache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected = TestDataProviderProvider.sortDomainModels(testDomain)

        val actual = citiesRepository.fetchCities()


        coVerify(exactly = 0) { cache[any()] }
        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnError() = runTest {
        coEvery { cache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.failure(Throwable("No internet")) }

        val result = citiesRepository.fetchCities()
        coVerify(exactly = 0) { cache[any()] }

        Truth
            .assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }


    @Test
    fun returnCacheDataIfNotEmpty() = runTest {
        coEvery { cache.isEmpty() } answers { false }
        coEvery { cache[any()] } answers { TestDataProviderProvider.sortDomainModels(testDomain) }
        val expected = TestDataProviderProvider.sortDomainModels(testDomain)


        val actual = citiesRepository.fetchCities()

        coVerify { cache[any()] }
        coVerify(exactly = 0) { dataSource.fetchCities() }
        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun saveDataToCache() = runTest {
        coEvery { cache.isEmpty() } answers { true }
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected = TestDataProviderProvider.sortDomainModels(testDomain)

        val actual = citiesRepository.fetchCities()

        coVerify { dataSource.fetchCities() }
        coVerify { cache[any()] = any() }
        coVerify(exactly = 0) { cache[any()]}
        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }
}






