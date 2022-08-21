package com.example.citysearch.domain

import com.google.common.truth.Truth
import org.junit.Test

class PagerShould {

    @Test
    fun returnNotCalculatedNextPage() {
        val pager = Pager()
        Truth.assertThat(pager.getNextPage(PAGE_STAY)).isEqualTo(0)
    }

    @Test
    fun returnForwardNextPage() {
        val pager = Pager(1)
        Truth.assertThat(pager.getNextPage(PAGE_FORWARD)).isEqualTo(1)
    }

    @Test
    fun returnBackwardNextPage() {
        val pager = Pager(1)
        pager.getNextPage(PAGE_FORWARD)
       val page =  pager.getNextPage(PAGE_BACKWARD)
        Truth.assertThat(page).isEqualTo(0)
    }

    @Test
    fun returnBackwardTwiceNextPage() {
        val pager = Pager(1)
        pager.getNextPage(PAGE_FORWARD)
        pager.getNextPage(PAGE_BACKWARD)
       val page =  pager.getNextPage(PAGE_BACKWARD)
        Truth.assertThat(page).isEqualTo(0)
    }

    @Test
    fun returnPageSize() {
        val pager = Pager(50)
        Truth.assertThat(pager.getPageSize()).isEqualTo(50)
    }

    @Test
    fun updateCurrentPage() {
        val pager = Pager()
        pager.setCurrentPage(5)
        Truth.assertThat(pager.getNextPage(PAGE_STAY)).isEqualTo(5)
    }

    @Test
    fun notUpdateCurrentPageToInValid() {
        val pager = Pager()
        pager.setCurrentPage(-3)
        Truth.assertThat(pager.getNextPage(PAGE_STAY)).isEqualTo(0)
    }
}