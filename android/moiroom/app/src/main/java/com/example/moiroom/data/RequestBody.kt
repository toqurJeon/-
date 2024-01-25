package com.example.moiroom.data

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