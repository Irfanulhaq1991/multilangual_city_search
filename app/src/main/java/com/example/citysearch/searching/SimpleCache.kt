package com.example.citysearch.searching

import androidx.lifecycle.LiveData
import com.example.citysearch.fetching.domain.City
import java.lang.IllegalStateException
import java.util.function.ToDoubleBiFunction

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
