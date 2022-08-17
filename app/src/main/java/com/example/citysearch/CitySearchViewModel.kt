package com.example.citysearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CitySearchViewModel {
    private val _citiesLiveData = MutableLiveData<String>()
    val citiesLiveData: LiveData<String> = _citiesLiveData

    fun fetchCities() {
        TODO("Not yet implemented")
    }

}
