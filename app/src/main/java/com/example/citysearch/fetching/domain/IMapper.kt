package com.example.citysearch.fetching.domain

interface IMapper<I,O>{
   fun map(cities: I):O
}