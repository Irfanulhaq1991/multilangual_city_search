package com.example.citysearch.fetching.view

import com.example.citysearch.fetching.domain.City


data class CitiesUIState(
    val loading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val cities: List<City> = emptyList()
)