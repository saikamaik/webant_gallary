package com.example.domain.entity

data class PhotoModel(
    val id: Int,
    val name: String?,
    val description: String?,
    val new: Boolean,
    val popular: Boolean,
    val image: Image,
    val user: String
)