package com.example.citysearch.data


data class CityDto(
    val country: String,
    val name: String,
    val _id: Int,
    val coord: CoordinatesDto
)

data class CoordinatesDto(
    val lon: Double,
    val lat: Double
)
