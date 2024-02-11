package com.example.moiroom.data

data class MatchedMember(
    val memberId: Long,
    val memberProfileImageUrl: String,
    val memberNickname: String,
    val memberGender: String,
    val memberBirthYear: Int,
    val metropolitanName: String,
    val cityName: String,
    val memberIntroduction: String,
    val characteristic: Characteristic,
    val interests: List<Interest>
)
