package com.example.moiroom.data

import kotlinx.serialization.Serializable

@Serializable
data class Metropolitan(
    val metropolitanId: Int,
    val metropolitanName: String
)
@Serializable
data class MetropolitanData(
    val metropolitan: List<Metropolitan>
)

@Serializable
data class MetropolitanResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: List<Metropolitan>
)

@Serializable
data class MyResponse2(val latitude: String, val longitude: String)

@Serializable
data class City(
    val cityId: Int,
    val cityName: String
)

@Serializable
data class CityData(
    val city: List<City>
)

@Serializable
data class CityResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: List<City>
)

@Serializable
data class MemberInfoRequest(
    val memberProfileImage: String,
    val metropolitanId: Long,
    val cityId: Long,
    val memberGender: String,
    val memberNickname: String,
    val memberIntroduction: String,
    val roommateSearchStatus: Int
)
@Serializable
data class MemberInfo(
    val memberId: Int,
    val socialId: Long,
    val provider: String,
    val metropolitanId: Int,
    val cityId: Int,
    val characteristicId: Int,
    val name: String,
    val imageUrl: String,
    val birthyear: String,
    val birthday: String,
    val gender: String,
    val roommateSearchStatus: Int,
    val introduction: String,
    val nickname: String,
    val accessToken: String,
    val refreshToken: String,
    val loginStatus: Int,
    val accountStatus: Int
)

// 서버 응답을 담는 데이터 클래스
@Serializable
data class MemberResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: MemberInfo
)
