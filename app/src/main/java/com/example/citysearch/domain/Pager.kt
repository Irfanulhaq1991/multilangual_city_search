package com.example.citysearch.domain

class Pager(private val pageSize: Int = 20) {
    private var currentPage = 0
    fun getNextPage(): Int {
        return currentPage + pageSize
    }

    fun getPageSize(): Int {
        return -1
    }


    fun setCurrentPage(currentPage: Int) {

    }

}
