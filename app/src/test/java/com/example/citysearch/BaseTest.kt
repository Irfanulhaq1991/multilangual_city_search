package com.example.citysearch

import io.mockk.MockKAnnotations

open class BaseTest {
   open fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
   }

     fun <T> isFailureWithMessage(result: Result<T>, message: String): Boolean {
        var errorMessage: String? = "#-#"
        result.onFailure { errorMessage = it.message }
        return result.isFailure && errorMessage == message
    }
}
