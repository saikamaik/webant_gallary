package com.example.domain.entity

data class PaginationModel<M: Model>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val countOfPages: Int,
    val model: M
)
