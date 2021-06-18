package com.example.domain.entity

data class APIResponse(
    val totalItems: Int,
    val itemsPerPage: Int,
    val countOfPages: Int,
    val data: List<PhotoModel>
)