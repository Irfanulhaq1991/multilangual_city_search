package com.example.citysearch.domain

import com.example.citysearch.BaseTest
import com.example.citysearch.TestDataProvider
import com.example.citysearch.data.CitiesRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchCitiesUseCaseShould : BaseTest() {


    lateinit var  fetchCitiesUseCase: FetchCitiesUseCase

    @RelaxedMockK
    lateinit var citiesRepository: CitiesRepository

    @Before
    override fun setUp() {
        super.setUp()
        fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
    }



    // White box test to verify the behavior
    @Test
    fun fetchCities() = runTest {
        coEvery { citiesRepository.fetchCities(any(),any()) } answers { Result.success(TestDataProvider.provideDomainModels()) }
        fetchCitiesUseCase()
        coVerify { citiesRepository.fetchCities(any(),any()) }
    }




}