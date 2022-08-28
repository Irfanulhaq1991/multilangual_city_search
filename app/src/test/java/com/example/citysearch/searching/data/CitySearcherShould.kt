package com.example.citysearch.searching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.TestDataProviderProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.CitySearcher
import com.example.citysearch.searching.SimpleCache
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
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
    fun returnNoCityOnShortQuery() = runTest {
        coEvery { cache[any()] } answers { TestDataProviderProvider.provideDomainModels() }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("##")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnNoCityOnLongQuery(){
        coEvery { cache[any()] } answers { TestDataProviderProvider.provideDomainModels() }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("Abbbbbbbbbbbbbbbbbbbbbbbbbbbbb")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }



    @Test
    fun returnOneCityOnExactQuery() {
        val data = TestDataProviderProvider.provideDomainModels()
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("Aberystwyth")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(1)
        Truth
            .assertThat(actual[0].cityName)
            .isEqualTo("Aberystwyth")
    }


    @Test
    fun returnManyCitiesPrefixedA() {
        val data = TestDataProviderProvider.provideDomainModels()
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("A")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(34)
    }

    @Test
    fun returnManyCitiesPrefixedB() {
        val data = TestDataProviderProvider.provideDomainModels()
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("B")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(67)
    }


    @Test
    fun returnManyCitiesPrefixedC() {
        val data = TestDataProviderProvider.provideDomainModels()
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("C")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(74)
    }


}