package com.example.citysearch.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import com.example.citysearch.searching.domain.SearchCityUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CitiesViewModel(
    private val fetchCitiesUseCase: FetchCitiesUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {
    private val _citiesLiveData = MutableLiveData<CitiesUIState>()

    /** guarding the mutable live data available only for local mutation*/
    val citiesLiveData: LiveData<CitiesUIState> = _citiesLiveData

    private var job: Job? = null

    /** cancel nay request mad previously proceed with new request */
    fun fetchCities() {
        job?.cancel()
        job = proceedFetchingCities()
    }


    fun search(query: String) {
        job = viewModelScope.launch {
            searchCityUseCase(query).run { reduceState(this) }
        }
    }

    /** After ui is updated */
    fun stateRendered() {
        _citiesLiveData.value = _citiesLiveData.value!!.copy(errorMessage = null,isUpdates = false)
    }



    /** request for fetching cities*/
    private fun proceedFetchingCities(): Job {
        return viewModelScope.launch {
            _citiesLiveData.value = (_citiesLiveData.value ?: CitiesUIState()).copy(loading = true)
            fetchCitiesUseCase()
                .run { reduceState(this) }

        }
    }

    /** reduce the response to the UI state*/
    private fun reduceState(result: Result<List<City>>) {
        result.fold({

            _citiesLiveData.value = _citiesLiveData.value?.copy(loading = false, cities = it,isUpdates = true)
        }, {
            _citiesLiveData.value =
                _citiesLiveData.value?.copy(loading = false, errorMessage = it.message!!)
        })
    }


}
