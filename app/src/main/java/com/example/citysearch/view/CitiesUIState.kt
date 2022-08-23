package com.example.citysearch.view

import com.example.citysearch.domain.City


data class CitiesUIState(
    val loading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val cities: List<City> = emptyList()
)