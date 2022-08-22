package com.example.citysearch.data.cache

interface IAppCache<K, V> {
    operator fun set(key: K, value: V)
    operator fun get(key: K): V
    fun isEmpty(): Boolean
    fun clear()

}