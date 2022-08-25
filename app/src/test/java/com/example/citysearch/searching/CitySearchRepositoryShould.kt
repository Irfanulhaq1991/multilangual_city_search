package com.example.citysearch.searching

import com.example.citysearch.common.BaseTest
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

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