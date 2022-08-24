package com.example.citysearch.fetching.domain

import com.example.citysearch.common.BaseTest
import com.example.citysearch.fetching.data.TestDataProviderProvider
import com.example.citysearch.fetching.data.CitiesRepository
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
    override fun setup() {
        super.setup()
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