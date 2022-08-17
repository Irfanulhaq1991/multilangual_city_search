package com.example.citysearch

import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.Test

class CitiesRepositoryShould : BaseTest(){
   // At the moment, No idea of the complete logic and design of the citiesRepository

    @Test
    fun returnNoCity(){
        val citiesRepository = CitiesRepository()
        Truth.assertThat(citiesRepository.fetchCities()).isEqualTo(emptyList<String>())
    }


}