package com.example.citysearch.domain

interface IMapper<I,O>{
    fun map(cities: I):O
}