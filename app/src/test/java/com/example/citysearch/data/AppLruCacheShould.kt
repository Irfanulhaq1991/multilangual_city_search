package com.example.citysearch.data

import com.example.citysearch.City

class AppLruCacheShould : AppCacheContractTest() {
    override fun withNullType(): IAppCache<String?, List<City>?> {
        return AppLruCache()
    }

    override fun withNonNullType(): IAppCache<String, List<City>> {
        return AppLruCache()
    }
}