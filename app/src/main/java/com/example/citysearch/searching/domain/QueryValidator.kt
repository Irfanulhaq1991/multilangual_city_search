package com.example.citysearch.searching.domain

import java.util.function.ToDoubleBiFunction

class QueryValidator {
    val regex = Regex("[!@#$%^&*()_+=?/>.<,~`]")
    fun validate(query: String):Boolean {
        if(query.contains(regex))
            return false
        else
            return true
    }

}
