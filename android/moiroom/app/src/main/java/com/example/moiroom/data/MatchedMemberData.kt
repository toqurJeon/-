package com.example.moiroom.data

import kotlinx.serialization.Serializable

@Serializable
data class MatchedMemberData(
    val member: MatchedMember,
    val matchRate: Int,
    val matchIntroduction: String
)
