package com.example.citysearch.view

import com.example.citysearch.fetching.domain.City


data class CitiesUIState(
    val loading: Boolean = false,
    val isError: Boolean = false,
    val isUpdates:Boolean = false,
    val errorMessage: String? = null,
    val cities: List<City> = emptyList()
)