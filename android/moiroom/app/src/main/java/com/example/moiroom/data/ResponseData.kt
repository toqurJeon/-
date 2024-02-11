package com.example.moiroom.data

data class ResponseData(
    val code: Int,
    val status: String,
    val message: String,
    val data: MatchedMemberList
)
