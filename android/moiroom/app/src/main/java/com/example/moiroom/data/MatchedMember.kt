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
    val matchRate: Int,
    var matchIntroduction: String,
    val socialbility: Int,
    val positivity: Int,
    val activity: Int,
    val communion: Int,
    val altruism: Int,
    val empathy: Int,
    val humor: Int,
    val generous: Int,
    val sleepAt: String,
    val wakeUpAt: String,
    val interest: List<Interest>
)
