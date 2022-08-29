package com.example.citysearch.searching.domain

import com.example.citysearch.common.BaseTest
import com.example.citysearch.searching.data.CitySearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchCityUseCaseShould : BaseTest() {

    @RelaxedMockK
    private lateinit var citySearchRepository: CitySearchRepository

    @RelaxedMockK
    private lateinit var queryValidator: QueryValidator

    private lateinit var searchCityUseCase: SearchCityUseCase

    @Before
    override fun setup() {
        super.setup()
        searchCityUseCase = SearchCityUseCase(citySearchRepository, queryValidator)
    }

    @Test
    fun searchCity() = runTest {
        coEvery { queryValidator.validate(any()) } answers { true }
        searchCityUseCase("##")
        coVerify { citySearchRepository.searchCity(any()) }
    }

    @Test
    fun validateQuery() = runTest {
        searchCityUseCase("##")
        coVerify { queryValidator.validate(any()) }
    }
}