package com.example.citysearch.domain

import com.example.citysearch.BaseTest
import com.example.citysearch.data.CitiesRepository
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyAll
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchCitiesUseCaseShould : BaseTest() {


    lateinit var fetchCitiesUseCase: FetchCitiesUseCase
    @RelaxedMockK
    lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
    }

    @Test
    fun fetchCities() = runTest {
        fetchCitiesUseCase()
        coVerify { citiesRepository.fetchCities() }

    }

}