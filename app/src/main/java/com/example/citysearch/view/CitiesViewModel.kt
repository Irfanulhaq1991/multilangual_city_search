package com.example.citysearch.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.citysearch.City
import com.example.citysearch.domain.FetchCitiesUseCase

class CitiesViewModel(private val fetchCitiesUseCase: FetchCitiesUseCase) {
    private val _citiesLiveData = MutableLiveData(CitiesUIState())
    val citiesLiveData: LiveData<CitiesUIState> = _citiesLiveData  // guarding the mutable live data available one for local mutation

    // request for fetching cities and reduce the response to the UI state
    fun fetchCities() {
        _citiesLiveData.value = _citiesLiveData.value!!.copy(loading = true)
        fetchCitiesUseCase()
            .fold({
            _citiesLiveData.value = _citiesLiveData.value!!.copy(
                loading = false,
                cities = it
            )
        }, {
            _citiesLiveData.value = _citiesLiveData.value!!.copy(
                loading = false,
                errorMessage =  it.message!!
            )
        })
    }

    fun errorMessageShown(){
        _citiesLiveData.value = _citiesLiveData.value!!.copy(errorMessage = null)
    }

}
