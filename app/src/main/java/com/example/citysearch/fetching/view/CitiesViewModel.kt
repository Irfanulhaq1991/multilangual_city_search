package com.example.citysearch.fetching.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CitiesViewModel(private val fetchCitiesUseCase: FetchCitiesUseCase) : ViewModel() {
    private val _citiesLiveData = MutableLiveData<CitiesUIState>()
    val citiesLiveData: LiveData<CitiesUIState> =
        _citiesLiveData  // guarding the mutable live data available one for local mutation

    private var fetchJob: Job? = null

    // cancel nay request mad previously proceed with new request
    fun fetchCities() {
        fetchJob?.cancel()
        proceed()

    }

    // request for fetching cities
    private fun proceed() {
        fetchJob = viewModelScope.launch {
            _citiesLiveData.value = (_citiesLiveData.value ?: CitiesUIState()).copy(loading = true)
            fetchCitiesUseCase()
                .run { reduceState(this) }

        }
    }


    //  reduce the response to the UI state
    private fun reduceState(result: Result<List<City>>) {
        result.fold({

            _citiesLiveData.value = _citiesLiveData.value?.copy(
                loading = false,
                cities = it
            )
        }, {
            _citiesLiveData.value = _citiesLiveData.value?.copy(
                loading = false,
                errorMessage = it.message!!
            )
        })
    }


    fun search(query: String) {
        TODO("Not yet implemented")
    }




    fun errorMessageShown() {
        _citiesLiveData.value = _citiesLiveData.value!!.copy(errorMessage = null)
    }



}
