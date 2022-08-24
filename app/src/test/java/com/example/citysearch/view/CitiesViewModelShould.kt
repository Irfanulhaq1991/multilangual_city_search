package com.example.citysearch.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.citysearch.common.BaseTest
import com.example.citysearch.common.CoroutineTestRule
import com.example.citysearch.data.TestDataProviderProvider
import com.example.citysearch.domain.FetchCitiesUseCase
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
        coEvery { fetchCitiesUseCase() } answers { Result.success(TestDataProviderProvider.provideDomainModels()) }

        viewModel.fetchCities()
        coVerify { fetchCitiesUseCase()}
    }

}