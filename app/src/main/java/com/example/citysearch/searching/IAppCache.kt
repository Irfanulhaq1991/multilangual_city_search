package com.example.citysearch.searching

interface IAppCache<K, V> {
    operator fun get(key: K): V
    operator fun set(key: K, data: V)
    fun isEmpty(): Boolean
    fun isFull(): Boolean
}