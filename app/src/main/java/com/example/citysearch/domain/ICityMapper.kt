package com.example.citysearch.domain

import com.example.citysearch.City
import com.example.citysearch.Coordinates
import com.example.citysearch.data.CityDto

class ICityMapper: IMapper<List<CityDto>, List<City>> {
    override fun map(cities: List<CityDto>): List<City> {
        return cities.map { City(it._id,it.name,it.country,
            Coordinates(it.coordinates.lon,it.coordinates.lat)
        ) }    }
}