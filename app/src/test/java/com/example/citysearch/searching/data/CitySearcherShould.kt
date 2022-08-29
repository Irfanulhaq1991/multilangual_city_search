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
    fun returnNoCity1() = runTest {
        coEvery { cache[any()] } answers { TestDataProviderProvider.provideDomainModelsFromBeginning() }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("##")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }



    @Test
    fun returnNoCity2(){
        coEvery { cache[any()] } answers { TestDataProviderProvider.provideDomainModelsFromBeginning() }
        val expected = Result.success(emptyList<City>())
        val actual = citySearcher.searchCity("Abbbbbbbbbbbbbbbbbbbbbbbbbbbbb")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }





    @Test
    fun returnOneCity1() {
        val data = TestDataProviderProvider.provideDomainModelsFromBeginning()
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
    fun returnOneCity2() {
        val data = TestDataProviderProvider.provideDomainModelsFromBeginning()
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
    fun returnOneCity3(){
        val data = TestDataProviderProvider.provideDomainModelFromEnding()
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
    fun returnManyCities1() {
        val data = TestDataProviderProvider.sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("A")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(34)
    }

    @Test
    fun returnManyCities2() {
        val data = TestDataProviderProvider.sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("B")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(67)
    }


    @Test
    fun returnManyCities3() {
        val data = TestDataProviderProvider.sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("C")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(74)
    }


    @Test
    fun returnManyCities4() {
        val data = TestDataProviderProvider.sortDomainModels(TestDataProviderProvider.provideDomainModelFromEnding())

        coEvery { cache[any()] } answers { data }

        var actual = emptyList<City>()
        citySearcher.searchCity("Z")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(10)
    }

}