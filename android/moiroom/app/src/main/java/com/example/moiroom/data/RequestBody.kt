package com.example.moiroom.data

import com.google.gson.annotations.SerializedName

data class RequestBody(
    val param1: Double,
    val param2: Double,
    val param3: Double
)

data class MyResponse(
    val message: String,
    val status: String
) {
    constructor() : this("", "")
}

data class PhotoInfo(
    val photoPath: String,
    val photoDayAdded: String,
    val photoIsDownload: String,
    val photoIsFavorite: String,
    val photoIsTrashed: String,
    val photoHeight: String,
    val photoWidth: String,
    val photoLatitude: String,
    val photoLongitude: String,
)
//?client_id=802744445198772
//&redirect_uri=https://example.com/instagramredirection
//&scope=user_profile,user_media
//&response_type=code
data class InstagramRequest(
    val client_id: String,
    val client_secret: String,
    val grant_type: String,
    val redirect_uri: String,
    val code: String
)

data class InstaResponse(
    val access_token: String,
    val user_id: Int
) {
    constructor() : this("", 0)
}

data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userId: String?,
    // 다른 필요한 응답 필드들을 추가할 수 있습니다.
)