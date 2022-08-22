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
        pager.totalCount = 1
        val expected = 1

        val actual = pager.getNextPage(PAGE_FORWARD)

        Truth
            .assertThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun returnBackwardNextPage() {
        val pager = Pager(1)

        pager.getNextPage(PAGE_FORWARD)
       val page =  pager.getNextPage(PAGE_BACKWARD)

        Truth
            .assertThat(page)
            .isEqualTo(0)
    }

    @Test
    fun returnBackwardTwiceNextPage() {
        val pager = Pager(1)

        pager.getNextPage(PAGE_FORWARD)
        pager.getNextPage(PAGE_BACKWARD)

       val page =  pager.getNextPage(PAGE_BACKWARD)

        Truth
            .assertThat(page)
            .isEqualTo(0)
    }

    @Test
    fun returnPageSize() {
        val pager = Pager(50)
        Truth.assertThat(pager.getPageSize()).isEqualTo(50)
    }

    @Test
    fun updateCurrentPage() {
        val pager = Pager()
        pager.currentPage = 5
        Truth
            .assertThat(pager.getNextPage(PAGE_STAY))
            .isEqualTo(5)
    }

    @Test
    fun notUpdateCurrentPageToInValid() {
        val pager = Pager()
        pager.currentPage =-3
        Truth
            .assertThat(pager.getNextPage(PAGE_STAY))
            .isEqualTo(0)
    }

    @Test
    fun haveZeroTotalCount(){
        val pager = Pager()

        Truth
            .assertThat(pager.totalCount)
            .isEqualTo(0)
    }

    @Test
    fun changeTotalCount(){
        val pager = Pager()

        pager.totalCount = 200

        Truth
            .assertThat(pager.totalCount)
            .isEqualTo(200)
    }

    @Test
    fun notExceedPageThanTotalCount(){
        val pager = Pager(100)

        pager.totalCount = 200
        pager.currentPage =pager.getNextPage(PAGE_FORWARD)
        pager.currentPage =pager.getNextPage(PAGE_FORWARD)
        pager.currentPage =pager.getNextPage(PAGE_FORWARD)
        val page = pager.getNextPage(PAGE_FORWARD)

        Truth
            .assertThat(pager.totalCount)
            .isEqualTo(page)
    }

    @Test
    fun notSetInvalidTotalCount(){
        val pager = Pager()

        pager.totalCount = -1

        Truth
            .assertThat(pager.totalCount)
            .isEqualTo(0)
    }
}