package com.example.citysearch.fetching.data.localfile

import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.ICitiesDataSourceContactTests


class FileDataSourceShould : ICitiesDataSourceContactTests() {


    override fun withNoData() = FileDataSource(object : JsonDataProvider() {
        override fun getJsonCitiesFromAssets(): String {
            return "##"
        }

        override fun deSerializeAllCitiesJson(json: String): List<CityDto> {
            return emptyList()
        }
    })

    override fun withData(cities: List<CityDto>) = FileDataSource(object : JsonDataProvider() {
        override fun getJsonCitiesFromAssets(): String {
            return "##"
        }

        override fun deSerializeAllCitiesJson(json: String): List<CityDto> {
            return cities
        }
    })



    override fun withException(e: Exception) =  FileDataSource(object : JsonDataProvider() {
        override fun getJsonCitiesFromAssets(): String {
            throw e
        }
    })

    override fun errorMessage(): String {
        return "Could not get data"
    }


}