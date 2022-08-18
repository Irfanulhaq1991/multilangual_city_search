package com.example.citysearch.domain

interface IMapper<I,O>{
   suspend fun map(cities: I):O
}