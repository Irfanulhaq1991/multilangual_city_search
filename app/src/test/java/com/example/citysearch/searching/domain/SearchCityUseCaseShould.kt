package com.example.citysearch.searching.domain

import com.example.citysearch.common.BaseTest
import com.example.citysearch.searching.CitySearchRepository
import com.example.citysearch.searching.SearchCityUseCase
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchCityUseCaseShould : BaseTest(){

    @RelaxedMockK
    private lateinit var citySearchRepository: CitySearchRepository

    private lateinit var searchCityUseCase: SearchCityUseCase
    @Before
    override fun setup() {
        super.setup()
        searchCityUseCase = SearchCityUseCase(citySearchRepository)
    }

    @Test
    fun searchCity()= runTest{
        searchCityUseCase.invoke("##")
        coVerify { citySearchRepository.searchCity(any()) }
    }
}