package com.example.citysearch.fetching.domain

import java.io.Serializable

// City Domain Entity
data class City(
    val id: Int,
    val cityName: String,
    val cityCountry: String,
    val coordinates: Coordinates
):Serializable {

    //formatting for UI as per domain requirements
    fun getCityCountryString(): String {
        return "$cityName, $cityCountry";
    }

}

data class Coordinates(
    val longitude: Double,
    val latitude: Double
) : Serializable {

    //formatting for UI as per the domain requirements
    fun getCoordinatesString(): String {
        return "$longitude, $latitude";
    }
}




