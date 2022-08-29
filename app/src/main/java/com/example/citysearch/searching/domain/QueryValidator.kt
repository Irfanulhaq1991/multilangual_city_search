package com.example.citysearch.searching.domain

import java.util.function.ToDoubleBiFunction

/**
 * validate valid and invalid query
 */
class QueryValidator {
    private val regex = Regex("[!@#$%^&*()_+=?/>.<,~`]")
    fun validate(query: String):Boolean {
        return !query.contains(regex)
    }

}
