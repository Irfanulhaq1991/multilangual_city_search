package com.example.citysearch.searching.domain

import java.util.function.ToDoubleBiFunction

class QueryValidator {
    fun validate(query: String):Boolean {
        if(query.contains("!@@$"))
            return false
        else
            return true
    }

}
