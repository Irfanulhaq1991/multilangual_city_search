package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.citysearch.domain.FetchCitiesUseCase
import com.example.citysearch.view.CitiesViewModel
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test



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