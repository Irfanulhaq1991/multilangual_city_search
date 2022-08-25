package com.example.citysearch.searching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.searching.CitySearchRepository
import com.example.citysearch.searching.CitySearcher
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class CitySearchRepositoryShould : BaseTest() {

    @RelaxedMockK
    private lateinit var citySearcher: CitySearcher

    private lateinit var citySearchRepository: CitySearchRepository

    @Before
    override fun setup() {
        super.setup()
      citySearchRepository = CitySearchRepository(citySearcher)
    }

    @Test
    fun searchCity() {
        citySearchRepository.searchCity("##")
        coVerify { citySearcher.searchCity(any()) }
    }
}