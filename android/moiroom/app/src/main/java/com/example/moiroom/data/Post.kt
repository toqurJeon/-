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
data class MemberInfoUpdateRequest(
    val metropolitanId: Long,
    val cityId: Long,
    val memberGender: String,
    val memberNickname: String,
    val memberIntroduction: String
)