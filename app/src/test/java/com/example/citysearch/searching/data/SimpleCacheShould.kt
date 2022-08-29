package com.example.citysearch.searching.data

import com.example.citysearch.fetching.domain.City

class SimpleCacheShould: AppCacheTest() {
    override fun cacheWith(size: Int): IAppCache<String, List<City>> {
        return SimpleCache(size)
    }
}