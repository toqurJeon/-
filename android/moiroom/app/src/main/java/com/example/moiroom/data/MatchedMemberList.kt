package com.example.moiroom.data

data class MatchedMemberList(
    val content: List<MatchedMember>,
    val totalPages: Int,
    val totalElememts: Int,
    val currentPage: Int,
    val pageSize: Int
)
