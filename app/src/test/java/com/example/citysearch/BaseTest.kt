package com.example.citysearch

import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import org.junit.Test

open class BaseTest {

   open fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
   }

     fun <T> isFailureWithMessage(result: Result<T>, message: String): Boolean {
        var errorMessage: String? = "#-#"
        result.onFailure { errorMessage = it.message }
        return result.isFailure && errorMessage == message
    }


//    @Test
//    fun  testfile(){
//        val data2 = TestDataProvider.provideDomainModels()
//        Truth.assertThat(data2.size).isEqualTo( 1500)
//    }
}
