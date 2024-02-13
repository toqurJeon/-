package com.example.moiroom.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val code: Int,
    val status: String,
    val message: String,
    val data: MatchedMemberList
)
