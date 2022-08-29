package com.example.citysearch.searching.domain

import java.util.function.ToDoubleBiFunction

class QueryValidator {
    private val regex = Regex("[!@#$%^&*()_+=?/>.<,~`]")
    fun validate(query: String):Boolean {
        return !query.contains(regex)
    }

}
