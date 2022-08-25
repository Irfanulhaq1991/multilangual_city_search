package com.example.citysearch.searching.data

import com.example.citysearch.fetching.domain.City
import com.example.citysearch.searching.IAppCache
import com.example.citysearch.searching.SimpleCache

class SimpleCacheShould: AppCacheTest() {
    override fun cacheWith(size: Int): IAppCache<String, List<City>> {
        return SimpleCache(size)
    }
}