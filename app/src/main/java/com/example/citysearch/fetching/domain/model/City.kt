package com.example.citysearch.fetching.domain

import java.io.Serializable

/**
 * Domain Root aggregate
 * */
data class City(
    val id: Int,
    val cityName: String,
    val cityCountry: String,
    val coordinates: Coordinates
):Serializable {

    /**
     * Format [cityName] and [cityCountry]for UI as per domain requirements
     * */
    fun getCityCountryString(): String {
        return "$cityName, $cityCountry";
    }

}

/**
 * Domain value model
 * */
data class Coordinates(
    val longitude: Double,
    val latitude: Double
) : Serializable {

    /**
     * Format  latitude and longitude for UI as per the domain requirements
     * */
    fun getCoordinatesString(): String {
        return "$longitude, $latitude";
    }
}




