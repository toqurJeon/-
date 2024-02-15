package com.example.moiroom.data

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int,
    val pageSize: Int
)