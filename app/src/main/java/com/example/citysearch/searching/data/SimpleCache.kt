package com.example.citysearch.searching.data

import java.lang.IllegalStateException


/**
 * Implementation of  [IAppCache] to cache data in [HashMap]
 */
class SimpleCache<K,V>(private val size:Int) : IAppCache<K, V> {
    private val cache = HashMap<K,V>(size)


   override operator fun get(key: K): V {
        return cache[key]!!
    }

    override fun isEmpty():Boolean{
        return cache.isEmpty()
    }

    override operator fun set(key: K, data: V) {
        if(isFull()) throw IllegalStateException("Can not add more item to cache")
       cache[key] = data
    }

    override fun isFull(): Boolean {
       return size == cache.size
    }

}
