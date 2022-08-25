package com.example.citysearch.searching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.CitySearcher
import com.example.citysearch.searching.SimpleCache
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CitySearcherShould : BaseTest(){


    @RelaxedMockK
    private lateinit var cache: SimpleCache<String, List<City>>
    private lateinit var citySearcher: CitySearcher

    @Before
    override fun setup() {
        super.setup()
        citySearcher = CitySearcher(cache)
    }

    @Test
    fun fetchCities()= runTest{
        citySearcher.searchCity("##")
        coVerify { cache[any()] }
    }

}