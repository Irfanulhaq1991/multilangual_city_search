package com.example.citysearch.domain

import com.example.citysearch.BaseTest
import com.example.citysearch.TestDataProvider
import com.example.citysearch.data.CitiesRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyAll
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchCitiesUseCaseShould : BaseTest() {


    lateinit var fetchCitiesUseCase: FetchCitiesUseCase

    @MockK
    lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
    }



    // White box test to verify the behavior
    @Test
    fun fetchCities() = runTest {
        coEvery { citiesRepository.fetchCities() } answers { Result.success(TestDataProvider.provideDomainModels()) }
        fetchCitiesUseCase()
        coVerify { citiesRepository.fetchCities() }
    }


    // black box test to verify the state
    @Test
    fun sortTheCities() = runTest {

        coEvery { citiesRepository.fetchCities() } answers { Result.success(TestDataProvider.provideDomainModels()) }
        val expected = Result.success(TestDataProvider.sort(TestDataProvider.provideDomainModels()))

        val result = fetchCitiesUseCase()

        coVerify { citiesRepository.fetchCities() }
        Truth.assertThat(result)
            .isEqualTo(expected)
    }




}