package com.example.citysearch.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.citysearch.BaseTest
import com.example.citysearch.CoroutineTestRule
import com.example.citysearch.TestDataProvider
import com.example.citysearch.domain.FetchCitiesUseCase
import com.example.citysearch.domain.PAGE_STAY
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
    lateinit var fetchCitiesUseCase: FetchCitiesUseCase

    private lateinit var viewModel: CitiesViewModel

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = CitiesViewModel(fetchCitiesUseCase)
    }

    @Test
    fun fetchCities() = runTest{
        coEvery { fetchCitiesUseCase(PAGE_STAY) } answers { Result.success(TestDataProvider.provideDomainModels()) }

        viewModel.fetchCities(0)
        coVerify { fetchCitiesUseCase(PAGE_STAY)}
    }

}