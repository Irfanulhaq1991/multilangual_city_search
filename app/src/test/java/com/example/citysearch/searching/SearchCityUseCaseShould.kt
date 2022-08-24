package com.example.citysearch.searching

import com.example.citysearch.common.BaseTest
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
    fun search()= runTest{
        searchCityUseCase.invoke("##")
        coVerify { citySearchRepository.search() }
    }
}