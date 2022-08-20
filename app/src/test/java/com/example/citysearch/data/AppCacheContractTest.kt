package com.example.citysearch.data

import com.example.citysearch.BaseTest
import com.example.citysearch.City
import com.example.citysearch.TestDataProvider
import com.google.common.truth.Truth
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

abstract class AppCacheContractTest() : BaseTest() {

    @Before
    override fun setUp() {
    }

    @Test
    fun set() {
        val appCache = withNonNullType()
        appCache[CITY_CACHE_KEY] = TestDataProvider.provideDomainModels()
        Truth.assertThat(appCache.isEmpty()).isEqualTo(false)
    }

    @Test
    fun emptied() {
        val appCache = withNonNullType()
        appCache[CITY_CACHE_KEY] = TestDataProvider.provideDomainModels()
        appCache.clear()
        Truth.assertThat(appCache.isEmpty()).isEqualTo(true)
    }

    @Test
    fun get() {
        val appCache = withNonNullType()
        appCache[CITY_CACHE_KEY] = TestDataProvider.provideDomainModels()
        val v = appCache[CITY_CACHE_KEY]
        Truth.assertThat(v).isEqualTo(TestDataProvider.provideDomainModels())
    }

    @Test
    fun throwExceptionWhenSet() {
        val appCache = withNullType()
        assertThrows(IllegalArgumentException::class.java){
            appCache[CITY_CACHE_KEY] = null
        }
    }

    @Test
    fun throwExceptionWhenGet() {
        val appCache = withNonNullType()
        assertThrows(NullPointerException::class.java){
            appCache[CITY_CACHE_KEY]
        }
    }

    abstract fun withNullType():IAppCache<String?,List<City>?>

  abstract fun withNonNullType():IAppCache<String,List<City>>

}