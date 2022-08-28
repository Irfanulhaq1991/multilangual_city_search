package com.example.citysearch.searching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.TestDataProviderProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.CitySearcher
import com.example.citysearch.searching.SimpleCache
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CitySearcherShould : BaseTest() {

    @RelaxedMockK
    private lateinit var cache: SimpleCache<String, List<City>>
    private lateinit var citySearcher: CitySearcher

    @Before
    override fun setup() {
        super.setup()
        citySearcher = CitySearcher(cache)
    }

    @Test
    fun returnNoCity() = runTest {
        coEvery { cache[any()] } answers { TestDataProviderProvider.provideDomainModels() }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("##")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnOneCity() {
        val data = TestDataProviderProvider.provideDomainModels().subList(0, 5)
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("Aberystwyth")
            .fold({ actual = it},{})

        Truth
            .assertThat(actual)
            .hasSize(1)
        Truth
            .assertThat(actual[0].cityName)
            .isEqualTo("Aberystwyth")
    }


    @Test
    fun returnManyCities() {
        val data = TestDataProviderProvider.provideDomainModels()
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("A")
            .fold({ actual = it},{})

        Truth
            .assertThat(actual)
            .hasSize(34)
    }

}