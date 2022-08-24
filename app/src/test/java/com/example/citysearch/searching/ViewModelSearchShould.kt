package com.example.citysearch.searching

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.CoroutineTestRule
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import com.example.citysearch.fetching.view.CitiesViewModel
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelSearchShould :BaseTest(){
    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    @RelaxedMockK
    private lateinit var searchCityUseCase: SearchCityUseCase
    @RelaxedMockK
    private lateinit var fetchCitiesUseCaseCity: FetchCitiesUseCase

    private lateinit var viewModel:CitiesViewModel

    @Before
   override fun setup(){
       super.setup()
        viewModel = CitiesViewModel(fetchCitiesUseCaseCity,searchCityUseCase)

    }

    @Test
    fun searchCity() = runTest {
        val query = "##"
        viewModel.search(query)
        coVerify { searchCityUseCase(any()) }
    }
}