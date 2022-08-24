package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.citysearch.common.CoroutineTestRule
import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.CityDto
import com.example.citysearch.data.TestDataProviderProvider
import com.example.citysearch.data.localfile.FileDataSource
import com.example.citysearch.data.localfile.JsonDataProvider
import com.example.citysearch.domain.City
import com.example.citysearch.domain.CityMapper
import com.example.citysearch.domain.FetchCitiesUseCase
import com.example.citysearch.view.CitiesUIState
import com.example.citysearch.view.CitiesViewModel
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


    private lateinit var uiController: CitySearchSpyUiController

    private var dtoModels = emptyList<CityDto>()
    private var domainModels = emptyList<City>()


    @Before
    fun setup() {
        dtoModels = TestDataProviderProvider.provideDTOS()
        domainModels = TestDataProviderProvider.provideDomainModels()


        val fakeCitiesRemoteApi = AcceptanceTestJsonProvider(dtoModels)

        val dataSource = FileDataSource(fakeCitiesRemoteApi)
        val mapper = CityMapper()
        val citiesRepository = CitiesRepository(dataSource, mapper)
        val fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
        val viewModel = CitiesViewModel(fetchCitiesUseCase)
        uiController = CitySearchSpyUiController().apply { this.viewModel = viewModel }
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

}



//https://blog.cleancoder.com/uncle-bob/2014/05/14/TheLittleMocker.html
class CitySearchSpyUiController : LifecycleOwner {

    lateinit var viewModel: CitiesViewModel
    val uiStates = mutableListOf<CitiesUIState>() // Ui State list
    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    fun onCreate() {
        registry.currentState = Lifecycle.State.STARTED
        viewModel.citiesLiveData.observe(this, {
            uiStates.add(it)
            if (uiStates.size == 3) {
                countDownLatch.countDown()
            }
        })
    }


    fun fetchCities() {
        viewModel.fetchCities()
        countDownLatch.await(5000, TimeUnit.MILLISECONDS)

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