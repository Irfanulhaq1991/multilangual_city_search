package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify


class CitiesViewModelShould:BaseTest(){



    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    @RelaxedMockK
    lateinit var fetchCitiesUseCase: FetchCitiesUseCase

    private lateinit var viewModel: CitiesViewModel

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = CitiesViewModel(fetchCitiesUseCase)
    }

    @Test
    fun fetchCities(){
        viewModel.fetchCities()
        verify { fetchCitiesUseCase() }
    }

}