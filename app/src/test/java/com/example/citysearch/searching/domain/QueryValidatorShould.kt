package com.example.citysearch.searching.domain

import com.example.citysearch.common.BaseTest
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

import org.mockito.kotlin.any

class QueryValidatorShould : BaseTest(){

    private lateinit var queryValidator: QueryValidator

    @Before
   override fun setup(){
        super.setup()
        queryValidator = QueryValidator()
    }
    @Test
    fun validateValidQuery(){
        Truth
            .assertThat(queryValidator.validate("Hello"))
            .isTrue()
    }

    @Test
    fun validateInvalidQuery1(){
        Truth
            .assertThat(queryValidator.validate("!@@$"))
            .isFalse()
    }
    @Test
    fun validateInvalidQuery2(){
        Truth
            .assertThat(queryValidator.validate("!"))
            .isFalse()
    }
    @Test
    fun validateInvalidQuery3(){
        Truth
            .assertThat(queryValidator.validate("@"))
            .isFalse()
    }

    @Test
    fun validateInvalidQuery4(){
        Truth
            .assertThat(queryValidator.validate("%+"))
            .isFalse()
    }
    @Test
    fun validateInvalidQuery5(){
        Truth
            .assertThat(queryValidator.validate("Hello%+"))
            .isFalse()
    }

}