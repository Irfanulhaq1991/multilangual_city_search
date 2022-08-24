package com.example.citysearch.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.data.localfile.FileDataSource
import com.example.citysearch.domain.City
import com.example.citysearch.domain.CityMapper
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class CitiesRepositoryShould : BaseTest() {


    @RelaxedMockK
    private lateinit var dataSource: FileDataSource

    private lateinit var citiesRepository: CitiesRepository

    private var testDto = emptyList<CityDto>()
    private var testDomain = emptyList<City>()
    private var totalCount = 0

    @Before
    override fun setUp() {
        super.setUp()
        val mapper = CityMapper()
        citiesRepository = CitiesRepository(dataSource,  mapper)

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
        coEvery { dataSource.fetchCities() } answers { Result.success(emptyList()) }
        val expected = emptyList<City>()


        //When
        val result = citiesRepository.fetchCities()

        //then
        Truth
            .assertThat(result)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnOneCity() = runTest {
        coEvery {
            dataSource.fetchCities()
        } answers { Result.success(TestDataProviderProvider.sortDto(testDto).subList(0, 1)) }
        val expected = TestDataProviderProvider
            .sortDomainModels(testDomain.subList(0, 1))

        val actual = citiesRepository.fetchCities()

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }


    @Test
    fun returnAllCities() = runTest {
        coEvery { dataSource.fetchCities() } answers { Result.success(testDto) }
        val expected = TestDataProviderProvider.sortDomainModels(testDomain)

        val actual = citiesRepository.fetchCities()

        Truth
            .assertThat(actual)
            .isEqualTo(Result.success(expected))
    }

    @Test
    fun returnError() = runTest {
        coEvery { dataSource.fetchCities() } answers { Result.failure(Throwable("No internet")) }

        val result = citiesRepository.fetchCities()

        Truth.assertThat(isFailureWithMessage(result, "No internet")).isTrue()
    }


}






