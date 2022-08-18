package com.example.citysearch.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.citysearch.domain.FetchCitiesUseCase

class CitiesViewModel(private val fetchCitiesUseCase: FetchCitiesUseCase) {
    private val _citiesLiveData = MutableLiveData<String>()
    val citiesLiveData: LiveData<String> = _citiesLiveData

    fun fetchCities() {
       fetchCitiesUseCase()
    }

}
