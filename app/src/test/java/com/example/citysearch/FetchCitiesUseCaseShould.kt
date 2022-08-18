package com.example.citysearch.domain

import com.example.citysearch.BaseTest
import com.example.citysearch.data.CitiesRepository
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyAll
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
    fun fetchCities() {
        fetchCitiesUseCase()
        verifyAll { citiesRepository.fetchCities() }

    }

}