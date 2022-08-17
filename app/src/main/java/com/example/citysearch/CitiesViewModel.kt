package com.example.citysearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CitiesViewModel(private val fetchCitiesUseCase: FetchCitiesUseCase) {
    private val _citiesLiveData = MutableLiveData<String>()
    val citiesLiveData: LiveData<String> = _citiesLiveData

    fun fetchCities() {
       fetchCitiesUseCase()
    }

}
