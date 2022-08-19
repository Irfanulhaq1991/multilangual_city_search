package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.CityDto
import com.example.citysearch.data.ICitiesRemoteApi
import com.example.citysearch.data.RemoteDataSource
import com.example.citysearch.domain.FetchCitiesUseCase
import com.example.citysearch.domain.CityMapper
import com.example.citysearch.view.CitiesUIState
import com.example.citysearch.view.CitiesViewModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CitySearchShould {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    private lateinit var  uiController: CitySearchSpyUiController

    @Before
   fun setup(){

        val fakeCitiesRemoteApi = object : ICitiesRemoteApi{
            override suspend fun fetchCities(): Response<List<CityDto>> = Response.success(TestDataProvider.provideDTOS())
        }

        val remoteDataSource = RemoteDataSource(fakeCitiesRemoteApi)
        val mapper = CityMapper()
        val citiesRepository = CitiesRepository(remoteDataSource,mapper)
        val fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
        val viewModel = CitiesViewModel(fetchCitiesUseCase)
        uiController = CitySearchSpyUiController().apply { this.viewModel = viewModel }
        uiController.onCreate()

   }


    @Test
    fun fetchCities(){
        val expected = listOf(CitiesUIState(),CitiesUIState(loading = true),
            CitiesUIState(loading = false,cities = TestDataProvider.sort(TestDataProvider.provideDomainModels()))
        )
        uiController.fetchCities()
        val actual = uiController.uiStates
        Truth.assertThat(actual).isEqualTo(expected)
    }
}

class CitySearchSpyUiController:LifecycleOwner {

    lateinit var viewModel: CitiesViewModel
    val uiStates = mutableListOf<CitiesUIState>() // Ui State list
    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    fun onCreate(){
        registry.currentState = Lifecycle.State.STARTED
        viewModel.citiesLiveData.observe(this,{
            uiStates.add(it)
            if(uiStates.size == 3) {
                countDownLatch.countDown()
            }
        })
    }


    fun fetchCities() {
        viewModel.fetchCities()
        countDownLatch.await(5000, TimeUnit.MILLISECONDS)

    }

}