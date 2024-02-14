package com.example.moiroom.data


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
// import kotlinx.android.parcel.Parcelize
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

@Parcelize
data class UserResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: Data
) : Parcelable {
    @Parcelize
    data class Data(
        val member: Member
    ) : Parcelable {
        @Parcelize
        data class Member(
            val memberId: Int,
            val memberProfileImageUrl: String,
            val memberNickname: String,
            val memberGender: String,
            val memberName: String,
            val memberBirthYear: String,
            val metropolitanName: String,
            val cityName: String,
            val memberIntroduction: String,
            val memberRoommateSearchStatus: Int,
            val characteristic: Characteristic,
            val interests: List<Interest>
        ) : Parcelable
//            {
//            @Parcelize
//            data class Characteristic(
//                val sociability: Int,
//                val positivity: Int,
//                val activity: Int,
//                val communion: Int,
//                val altruism: Int,
//                val empathy: Int,
//                val humor: Int,
//                val generous: Int,
//                val sleepAt: String?,
//                val wakeUpAt: String?
//            ) : Parcelable


//            @Parcelize
//            data class Interest(
//                val interestName: String,
//                val interestPercent: Int
//            ) : Parcelable
        }
    }
