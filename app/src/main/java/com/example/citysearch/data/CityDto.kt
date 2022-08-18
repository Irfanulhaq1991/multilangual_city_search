package com.example.citysearch.data

import com.example.citysearch.Coordinates

data class CityDto(
    val country: String,
    val name: String,
    val _id: Int,
    val coordinates: CoordinatesDto
)

data class CoordinatesDto(
    val lon: Double,
    val lat: Double
)
