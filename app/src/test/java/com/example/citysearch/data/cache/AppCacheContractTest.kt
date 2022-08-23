package com.example.citysearch.data.cache

import com.example.citysearch.BaseTest
import com.example.citysearch.data.CITY_CACHE_KEY
import com.example.citysearch.data.TestDataProviderProvider
import com.example.citysearch.domain.City
import com.google.common.truth.Truth
import org.junit.Assert.assertThrows
import org.junit.Test
import java.lang.IllegalArgumentException

abstract class AppCacheContractTest() : BaseTest() {

    @Test
    fun set() {
        val appCache = withNonNullType()
        appCache[CITY_CACHE_KEY] = TestDataProviderProvider.provideDomainModels()
        Truth.assertThat(appCache.isEmpty()).isEqualTo(false)
    }

    @Test
    fun emptied() {
        val appCache = withNonNullType()
        appCache[CITY_CACHE_KEY] = TestDataProviderProvider.provideDomainModels()
        appCache.clear()
        Truth.assertThat(appCache.isEmpty()).isEqualTo(true)
    }

    @Test
    fun get() {
        val appCache = withNonNullType()
        appCache[CITY_CACHE_KEY] = TestDataProviderProvider.provideDomainModels()
        val v = appCache[CITY_CACHE_KEY]
        Truth.assertThat(v).isEqualTo(TestDataProviderProvider.provideDomainModels())
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

    abstract fun withNullType(): IAppCache<String?, List<City>?>

  abstract fun withNonNullType(): IAppCache<String, List<City>>

}