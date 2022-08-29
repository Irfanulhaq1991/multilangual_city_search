package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.CoroutineTestRule
import com.example.citysearch.common.TestDataProviderProvider
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import com.example.citysearch.view.CitiesViewModel
import com.example.citysearch.searching.SearchCityUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class CitiesViewModelShould: BaseTest(){

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    @RelaxedMockK
    private lateinit var searchCityUseCase: SearchCityUseCase
    @RelaxedMockK
    private lateinit var fetchCitiesUseCase: FetchCitiesUseCase

    private lateinit var viewModel: CitiesViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = CitiesViewModel(fetchCitiesUseCase,searchCityUseCase)
    }

    @Test
    fun fetchCities() = runTest{
        coEvery { fetchCitiesUseCase() } answers { Result.success(TestDataProviderProvider.provideDomainModelsFromBeginning()) }

        viewModel.fetchCities()
        coVerify { fetchCitiesUseCase()}
    }

    @Test
    fun searchCity() = runTest {
        coEvery { searchCityUseCase(any()) } answers { Result.success(TestDataProviderProvider.provideDomainModelsFromBeginning()) }

        val query = "##"
        viewModel.search(query)
        coVerify { searchCityUseCase(any()) }
    }
}