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
    fun returnOriginalOnEmpty() = runTest {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }
        val expected = Result.success(data)
        val actual = citySearcher.searchCity("")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }


    @Test
    fun returnNoCityOnInvalid() = runTest {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("##")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }



    @Test
    fun returnNoCityOnLonger(){
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
                coEvery { cache[any()] } answers { data }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("Abbbbbbbbbbbbbbbbbbbbbbbbbbbbb")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }





    @Test
    fun returnOneCityOnExact() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
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
    fun returnOneCityOnExact2() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("Alesund")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(1)
        Truth
            .assertThat(actual[0].cityName)
            .isEqualTo("Alesund")
    }




    @Test
    fun returnOneCityOnExactAndLong(){
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelFromEnding())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("ZezeAwazumachi")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(1)

        Truth
            .assertThat(actual[0].cityName)
            .isEqualTo("ZezeAwazumachi")

    }



    @Test
    fun returnManyCitiesOnShort1() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("A")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(34)
    }

    @Test
    fun returnManyCitiesOnShort2() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("B")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(67)
    }


    @Test
    fun returnManyCitiesOnShort3() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("C")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(74)
    }


    @Test
    fun returnManyCitiesOnShort4() {
        val data = TestDataProviderProvider.sortDomainModels(TestDataProviderProvider.provideDomainModelFromEnding())

        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("Z")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(10)
    }

    @Test
    fun returnErrorOnNoData(){
        coEvery { cache[any()] } throws NullPointerException()

        val expectedErrorMessage = "Error: Cache Corrupted"

        val actual = citySearcher.searchCity("###")

        Truth.assertThat(isFailureWithMessage(actual, expectedErrorMessage)).isTrue()
    }

}