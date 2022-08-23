package com.example.citysearch.data.cache

import com.example.citysearch.domain.City


class AppLruCacheShould : AppCacheContractTest() {
    override fun withNullType(): IAppCache<String?, List<City>?> {
        return AppLruCache()
    }

    override fun withNonNullType(): IAppCache<String, List<City>> {
        return AppLruCache()
    }
}