package com.example.citysearch.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.citysearch.BaseTest
import com.example.citysearch.CoroutineTestRule
import com.example.citysearch.DummyDataProvider
import com.example.citysearch.domain.FetchCitiesUseCase
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class CitiesViewModelShould: BaseTest(){

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
        every { fetchCitiesUseCase() } answers { Result.success(DummyDataProvider.provideDomainModels()) }

        viewModel.fetchCities()
        verify { fetchCitiesUseCase()}
    }

}