package com.example.citysearch.fetching.domain.mapper

interface IMapper<I,O>{
   fun map(cities: I):O
}