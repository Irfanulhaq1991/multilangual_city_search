package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.City
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class RemoteDataSourceShould : BaseTest() {

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun fetchNoCity() {
        val remoteDataSource = RemoteDataSource()
        Truth.assertThat(remoteDataSource.fetchCities()).isEqualTo(Result.success(emptyList<CityDto>()))
    }
}