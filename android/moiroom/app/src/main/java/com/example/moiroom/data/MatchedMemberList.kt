package com.example.moiroom.data

data class MatchedMemberList(
    val content: List<MatchedMemberData>,
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int,
    val pageSize: Int
)
