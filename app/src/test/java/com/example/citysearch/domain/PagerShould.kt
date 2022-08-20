package com.example.citysearch.domain

import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.Test

class PagerShould {

    @Test
    fun returnNextPage() {

        val pager = Pager()
        Truth.assertThat(pager.getNextPage()).isEqualTo(0)

    }

    fun returnPageSize() {}

    fun updateCurrentPage() {

    }
}