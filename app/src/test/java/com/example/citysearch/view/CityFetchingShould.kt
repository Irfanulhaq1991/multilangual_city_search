package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.citysearch.data.*
import com.example.citysearch.data.cache.AppLruCache
import com.example.citysearch.data.localfile.FileDataSource
import com.example.citysearch.data.localfile.JsonDataProvider
import com.example.citysearch.domain.*
import com.example.citysearch.view.CitiesUIState
import com.example.citysearch.view.CitiesViewModel
import com.google.common.truth.Truth
import org.junit.After
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

    private val pageSize = 2
    private var dto = emptyList<CityDto>()
    private var domain = emptyList<City>()


    @Before
    fun setup() {
        dto = TestDataProviderProvider.provideDTOS()
        domain = TestDataProviderProvider.provideDomainModels()


        val fakeCitiesRemoteApi = object : JsonDataProvider() {
            override fun getJsonCitiesFromAssets(): String? {
                return "##"
            }

            override fun deSerializeAllCitiesJson(json: String): List<CityDto> {
               return dto
            }

        }

        val dataSource = FileDataSource(fakeCitiesRemoteApi)
        val mapper = CityMapper()
        val appCache = AppLruCache<String, List<City>>()
        val citiesRepository = CitiesRepository(dataSource, appCache, mapper)
        val pager = Pager(pageSize)
        val fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository, pager)
        val viewModel = CitiesViewModel(fetchCitiesUseCase)
        uiController = CitySearchSpyUiController().apply { this.viewModel = viewModel }
        uiController.onCreate()

    }

    @After
    fun tearDown() {
        dto = emptyList<CityDto>()
        domain = emptyList<City>()
    }

    @Test
    fun fetchCities() {
        val expected = listOf(
            CitiesUIState(loading = true),
            CitiesUIState(loading = false,
                cities = TestDataProviderProvider.sortDomainModels(domain)
                    .subList(0, pageSize)
            )
        )

        uiController.fetchCities(PAGE_STAY)
        val actual = uiController.uiStates

        Truth.assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun scrollInBoThDirections() {

        //Given
        var expected = listOf(
            CitiesUIState(loading = true),
            CitiesUIState(loading = false,
                cities = TestDataProviderProvider.sortDomainModels(domain)
                    .subList(0, pageSize)
            ),
            CitiesUIState(loading = true,
                cities = TestDataProviderProvider.sortDomainModels(domain)
                    .subList(0, pageSize)
            ),
            CitiesUIState(loading = false,
                cities = TestDataProviderProvider.sortDomainModels(domain)
                    .subList(pageSize, pageSize+pageSize)
            ),
            CitiesUIState(loading = true,
                cities = TestDataProviderProvider.sortDomainModels(domain)
                    .subList(pageSize, pageSize+pageSize)
            ),
            CitiesUIState(loading = false,
                cities = TestDataProviderProvider.sortDomainModels(domain)
                    .subList(0, pageSize)
            )
        )


        //When
        uiController.fetchCities(PAGE_STAY)
        uiController.fetchCities(PAGE_FORWARD)
        uiController.fetchCities(PAGE_BACKWARD)

        //Then
       val  actual = uiController.uiStates
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


    fun fetchCities(scrollDir: Int) {
        viewModel.fetchCities(scrollDir)
        countDownLatch.await(5000, TimeUnit.MILLISECONDS)

    }

}