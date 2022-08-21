package com.example.citysearch.domain

const val PAGE_FORWARD = 1
const val PAGE_BACKWARD = -1
const val PAGE_STAY = 0
/**
 The paging is infinite as we don't have the the total count information at the moment 
 This could could be promoted to circular paging once we have the total count or could be just bounded

 */
class Pager(private val pageSize: Int = 20) {
    private var currentPage = 0
    fun getNextPage(direction: Int = PAGE_STAY): Int {
        return when (direction) {
            PAGE_BACKWARD -> if (currentPage - pageSize > 0) currentPage - pageSize else currentPage
            PAGE_FORWARD -> currentPage + pageSize
            else -> currentPage
        }
    }

    fun getPageSize(): Int {
        return pageSize
    }


    fun setCurrentPage(currentPage: Int) {
        if (currentPage < 0) return
        this.currentPage = currentPage
    }

}
