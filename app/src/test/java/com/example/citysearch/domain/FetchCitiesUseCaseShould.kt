package com.example.citysearch.domain

import com.example.citysearch.common.BaseTest
import com.example.citysearch.data.TestDataProviderProvider
import com.example.citysearch.data.CitiesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchCitiesUseCaseShould : BaseTest() {


    lateinit var fetchCitiesUseCase: FetchCitiesUseCase

    @RelaxedMockK
    private lateinit var citiesRepository: CitiesRepository


    private var domainModels = emptyList<City>()

    @Before
    override fun setUp() {
        super.setUp()
        domainModels = TestDataProviderProvider.provideDomainModels()
        fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
    }


    // White box test to verify the behavior
    @Test
    fun fetchCities() = runTest {
        coEvery {
            citiesRepository.fetchCities()
        } answers {
            Result.success(domainModels)
        }

        fetchCitiesUseCase()
        coVerify { citiesRepository.fetchCities() }
    }

}