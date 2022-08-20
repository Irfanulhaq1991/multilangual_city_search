package com.example.citysearch.domain

import com.google.common.truth.Truth
import org.junit.Test

class PagerShould {

    @Test
    fun returnNextPage() {
        val pager = Pager(1)
        Truth.assertThat(pager.getNextPage()).isEqualTo(1)

    }

    fun returnPageSize() {}

    fun updateCurrentPage() {

    }
}