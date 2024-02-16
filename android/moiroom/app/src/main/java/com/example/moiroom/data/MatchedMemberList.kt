package com.example.moiroom.data

import kotlinx.serialization.Serializable

@Serializable
data class MatchedMemberList(
    val content: List<MatchedMemberData>,
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int,
    val pageSize: Int
)
