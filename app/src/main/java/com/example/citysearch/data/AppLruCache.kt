package com.example.citysearch.data

import androidx.collection.LruCache
import java.lang.IllegalArgumentException

// Cache to store data in memory
class AppLruCache<K, V>(private val size:Int = 1) : IAppCache<K, V> {

    private val cache: LruCache<K, V> = LruCache(size)
    override operator fun set(key: K, value: V) {
        if(key == null || value == null) throw IllegalArgumentException()
         cache.put(key, value)
    }

    override fun isEmpty(): Boolean {
        return cache.size() == 0
    }

    override fun clear(){
        cache.evictAll()
    }

    override operator fun get(key: K): V {
        return cache[key!!]!!
    }

}
