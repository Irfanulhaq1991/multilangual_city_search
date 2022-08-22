package com.example.citysearch.domain

const val PAGE_FORWARD = 1
const val PAGE_BACKWARD = -1
const val PAGE_STAY = 0

/**
the Page is bounded by the size
 */

class Pager(private val pageSize: Int = 20) {
    var totalCount = 0
        set(value) {
            if (value >= 0)
                field = value
        }


    var currentPage = 0
        set(value) {
            if (value >= 0)
                field = value
        }

    fun getNextPage(direction: Int = PAGE_STAY): Int {
        return when (direction) {
            PAGE_BACKWARD -> if (currentPage - pageSize >= 0) currentPage - pageSize else currentPage
            PAGE_FORWARD -> if (currentPage <= totalCount - pageSize) currentPage + pageSize else totalCount - pageSize
            else -> currentPage
        }
    }

    fun getPageSize(): Int {
        return pageSize
    }


//    fun setCurrentPage(currentPage: Int) {
//        if (currentPage < 0) return
//        this.currentPage = currentPage
//    }

}
