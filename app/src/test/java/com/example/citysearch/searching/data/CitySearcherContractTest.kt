package com.example.citysearch.searching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.TestDataProviderProvider
import com.example.citysearch.fetching.domain.City
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.NullPointerException

abstract class CitySearcherContractTest : BaseTest() {


    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun returnOriginalOnEmpty() = runTest {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        val searcher = with(data)

        val expected = Result.success(data)
        val actual = searcher.searchCity("")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }


    @Test
    fun returnNoCityOnInvalid() = runTest {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        val searcher = with(data)

        val expected = Result.success(emptyList<City>())
        val actual = searcher.searchCity("##")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }


    @Test
    fun returnNoCityOnInvalid2() = runTest {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        val searcher = with(data)

        val expected = Result.success(emptyList<City>())
        val actual = searcher.searchCity(" ")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnNoCityOnLonger() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        val searcher = with(data)

        val expected = Result.success(emptyList<City>())
        val actual = searcher.searchCity("Abbbbbbbbbbbbbbbbbbbbbbbbbbbbb")

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }


    @Test
    fun returnOneCityOnExact() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())
        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("Aberystwyth")
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
        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("Alesund")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(1)
        Truth
            .assertThat(actual[0].cityName)
            .isEqualTo("Alesund")
    }


    @Test
    fun returnOneCityOnExactAndLong() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelFromEnding())
        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("ZezeAwazumachi")
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


        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("A")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(34)
    }


    @Test
    fun returnManyCitiesOnShort2() {
        val data = TestDataProviderProvider
            .sortDomainModels(
                TestDataProviderProvider
                    .provideDomainModelsFromBeginning()
            )

        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("B")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(67)
    }


    @Test
    fun returnManyCitiesOnShort3() {
        val data = TestDataProviderProvider
            .sortDomainModels(
                TestDataProviderProvider
                    .provideDomainModelsFromBeginning()
            )

        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("C")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(74)
    }


    @Test
    fun returnManyCitiesOnShort4() {
        val data =
            TestDataProviderProvider
                .sortDomainModels(
                    TestDataProviderProvider
                        .provideDomainModelFromEnding()
                )

        val searcher = with(data)

        var actual = emptyList<City>()

        searcher.searchCity("Z")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(10)
    }

    @Test
    fun returnManyCitiesSmallLetter() {
        val data = TestDataProviderProvider
            .sortDomainModels(TestDataProviderProvider.provideDomainModelsFromBeginning())


        val searcher = with(data)

        var actual = emptyList<City>()
        searcher.searchCity("a")
            .fold({ actual = it }, {})

        Truth
            .assertThat(actual)
            .hasSize(34)
    }


    @Test
    fun returnErrorOnNoData() {

        val expectedErrorMessage = errorMessage()
        val citySearcher = withException(NullPointerException())

        val actual = citySearcher.searchCity("###")
        Truth.assertThat(isFailureWithMessage(actual, expectedErrorMessage)).isTrue()
    }

    abstract fun with(data: List<City>): ICitySearcher
    abstract fun withException(e: Exception): ICitySearcher
    abstract fun errorMessage(): String


}