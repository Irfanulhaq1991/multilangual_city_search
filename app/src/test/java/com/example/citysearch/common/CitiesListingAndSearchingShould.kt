package com.example.citysearch.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.citysearch.fetching.data.CitiesRepository
import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.TestDataProviderProvider
import com.example.citysearch.fetching.data.localfile.FileDataSource
import com.example.citysearch.fetching.data.localfile.JsonDataProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.CityMapper
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import com.example.citysearch.fetching.view.CitiesUIState
import com.example.citysearch.fetching.view.CitiesViewModel
import com.example.citysearch.searching.SimpleCache
import com.example.citysearch.searching.CitySearchRepository
import com.example.citysearch.searching.CitySearcher
import com.example.citysearch.searching.SearchCityUseCase
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CityFetchingShould {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRul = CoroutineTestRule()


    private lateinit var uiController: CityFetchingSpyUiController

    private var dtoModels = emptyList<CityDto>()
    private var domainModels = emptyList<City>()


    @Before
    fun setup() {
        dtoModels = TestDataProviderProvider.provideDTOS()
        domainModels = TestDataProviderProvider.provideDomainModels()
        val fakeCitiesRemoteApi = AcceptanceTestJsonProvider(dtoModels)
        val dataSource = FileDataSource(fakeCitiesRemoteApi)
        val mapper = CityMapper()
        val cache = SimpleCache<String,List<City>>(1)

        val citiesRepository = CitiesRepository(dataSource, cache, mapper)
        val fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)

        val citySearcher = CitySearcher(cache)
        val citySearchRepository = CitySearchRepository(citySearcher)
        val searchCityUseCase = SearchCityUseCase(citySearchRepository)

        val viewModel = CitiesViewModel(fetchCitiesUseCase,searchCityUseCase)
        uiController = CityFetchingSpyUiController().apply { this.viewModel = viewModel }
        uiController.onCreate()

    }

    @Test
    fun fetchCities() {
        val expected = listOf(
            CitiesUIState(loading = true),
            CitiesUIState(loading = false,
                cities = TestDataProviderProvider.sortDomainModels(domainModels)
            )
        )

        uiController.fetchCities()
        val actual = uiController.uiStates

        Truth.assertThat(actual).isEqualTo(expected)
    }
    @Test
    fun searchCity() {
        val expected = listOf(
            CitiesUIState(
                loading = false,
                cities = TestDataProviderProvider.sortDomainModels(domainModels)
            )
        )

        uiController.fetchCities()
        uiController.clear()
        uiController.searchCity(CITY_LIST_KEY)
        val actual = uiController.uiStates

        Truth.assertThat(actual).isEqualTo(expected)
    }

}



//https://blog.cleancoder.com/uncle-bob/2014/05/14/TheLittleMocker.html
class CityFetchingSpyUiController : LifecycleOwner {

    lateinit var viewModel: CitiesViewModel
    val uiStates = mutableListOf<CitiesUIState>() // Ui State list
    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    fun onCreate() {
        registry.currentState = Lifecycle.State.STARTED
        viewModel.citiesLiveData.observe(this, {
            uiStates.add(it)
            if (uiStates.size == 2) {
                countDownLatch.countDown()
            }
        })
    }


    fun fetchCities() {
        viewModel.fetchCities()
        countDownLatch.await(1000, TimeUnit.MILLISECONDS)

    }

    fun searchCity(query: String) {
        viewModel.search(query)
        countDownLatch.await(5000, TimeUnit.MILLISECONDS)
    }

    fun clear(){
        uiStates.clear()
    }

}

class AcceptanceTestJsonProvider(val dtoModels:List<CityDto>): JsonDataProvider() {
    override fun getJsonCitiesFromAssets(): String? {
        return "##"
    }

    override fun deSerializeAllCitiesJson(json: String): List<CityDto> {
        return dtoModels
    }

}