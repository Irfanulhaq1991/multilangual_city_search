package com.example.citysearch.domain

import com.example.citysearch.BaseTest
import com.example.citysearch.City
import com.example.citysearch.data.TestDataProviderProvider
import com.example.citysearch.data.CitiesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchCitiesUseCaseShould : BaseTest() {


    lateinit var fetchCitiesUseCase: FetchCitiesUseCase

    @RelaxedMockK
    private lateinit var citiesRepository: CitiesRepository

    @RelaxedMockK
    private lateinit var pager: Pager
    private var domainModels = emptyList<City>()

    @Before
    override fun setUp() {
        super.setUp()
        domainModels = TestDataProviderProvider.provideDomainModels()
        fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository, pager)
    }


    // White box test to verify the behavior
    @Test
    fun fetchCities() = runTest {
        coEvery {
            citiesRepository.fetchCities(any(), any())
        } answers {
            Result.success(Pair(domainModels, domainModels.size))
        }

        fetchCitiesUseCase(PAGE_STAY)
        coVerify { citiesRepository.fetchCities(any(), any()) }
    }

    @Test
    fun getNextPage() = runTest {

        coEvery {
            citiesRepository.fetchCities(any(), any())
        } answers {
            Result.success(Pair(domainModels, domainModels.size))
        }


        fetchCitiesUseCase(PAGE_STAY)
        coVerify { pager.getNextPage(any()) }
    }

    @Test
    fun getPageSize() = runTest {

        coEvery {
            citiesRepository.fetchCities(any(), any())
        } answers {
            Result.success(Pair(domainModels, domainModels.size))
        }

        fetchCitiesUseCase(PAGE_STAY)

        coVerify { pager.getPageSize() }
    }

    @Test
    fun setCurrentPage() = runTest {
        coEvery {
            citiesRepository.fetchCities(any(), any())
        } answers {
            Result.success(Pair(domainModels, domainModels.size))
        }

        fetchCitiesUseCase(PAGE_STAY)

        coVerify { pager.currentPage = any() }
    }

    @Test
    fun setTotalCount() = runTest {
        coEvery {
            citiesRepository.fetchCities(any(), any())
        } answers {
            Result.success(Pair(domainModels, domainModels.size))
        }

        fetchCitiesUseCase(PAGE_STAY)

        coVerify { pager.totalCount = any() }
    }

}