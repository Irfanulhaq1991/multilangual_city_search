package com.example.citysearch

import com.example.citysearch.data.CityDto
import com.example.citysearch.data.CoordinatesDto

object DummyDataProvider {
     fun provideDTOS(): List<CityDto> {
        return listOf(

            CityDto(
                "UK", "London", 12345,
                CoordinatesDto(1.0, 1.0)
            ),
            CityDto(
                "UK", "Yorkshire", 123456,
                CoordinatesDto(2.0, 2.0)
            ),

            )
    }

    fun provideDomainModels(): List<City> {
        return listOf(
            City(
                12345, "London", "UK",
                Coordinates(1.0, 1.0)
            ),
            City(
                123456, "Yorkshire", "UK",
                Coordinates(2.0, 2.0)
            ),

            )
    }
}