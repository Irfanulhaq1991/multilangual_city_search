package com.example.citysearch.searching.data

import com.example.citysearch.common.BaseTest
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.IAppCache
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException
import kotlin.test.assertFailsWith

abstract class AppCacheTest : BaseTest() {

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun initiallyEmpty() {
        val cache = cacheWith(1)
        Truth
            .assertThat(cache.isEmpty())
            .isTrue()
        Truth
            .assertThat(cache.isFull())
            .isFalse()
    }

    @Test
    fun notEmptyNotFull() {
        val cache = cacheWith(2)
        cache["##"] = emptyList()
        Truth
            .assertThat(cache.isEmpty())
            .isFalse()
        Truth
            .assertThat(cache.isFull())
            .isFalse()
    }

    @Test
    fun put() {
        val cache = cacheWith(1)
        cache["##"] = emptyList()
        Truth
            .assertThat(cache.isEmpty())
            .isFalse()
    }

    @Test
    fun get(){
        val cache = cacheWith(1)
        cache["##"] = emptyList()
        Truth
            .assertThat(cache["##"])
            .isEqualTo(emptyList<City>())
    }

    @Test
    fun reachFull(){
        val cache = cacheWith(1)
        cache["##"] = emptyList()
        Truth
            .assertThat(cache.isFull())
            .isTrue()
    }


    @Test
    fun notPuntInFull(){
        val cache = cacheWith(1)
        cache["##"] = emptyList()

        assertFailsWith<IllegalStateException> {
            cache["###"] = emptyList()
        }
    }

    @Test
    fun throwNullPointerException(){
        val cache = cacheWith(1)
        assertFailsWith<NullPointerException> {
            cache["##"]
        }
    }

    abstract fun cacheWith(size: Int): IAppCache<String, List<City>>

}