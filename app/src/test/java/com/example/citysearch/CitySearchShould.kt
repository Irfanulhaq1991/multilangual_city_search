package com.example.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.RemoteDataSource
import com.example.citysearch.domain.FetchCitiesUseCase
import com.example.citysearch.domain.ICityMapper
import com.example.citysearch.view.CitiesViewModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

        val remoteDataSource = RemoteDataSource(fakeCitiesRemoteApi)
        val mapper = ICityMapper()
        val citiesRepository = CitiesRepository(remoteDataSource,mapper)
        val fetchCitiesUseCase = FetchCitiesUseCase(citiesRepository)
        val viewModel = CitiesViewModel(fetchCitiesUseCase)
        uiController = CitySearchSpyUiController().apply { this.viewModel = viewModel }
        uiController.onCreate()

   }


    @Test
    fun fetchCities(){
        val expected = listOf("loading","Success","HideLoading")
        uiController.fetchCities()
        val actual = uiController.uiStates
        Truth.assertThat(actual).isEqualTo(expected)
    }
}

class CitySearchSpyUiController:LifecycleOwner {

    lateinit var viewModel: CitiesViewModel
    val uiStates = mutableListOf<String>() // No idea of the exact type of the states at the moment
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
        countDownLatch.await(100, TimeUnit.MILLISECONDS)

    }




}