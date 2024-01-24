package com.example.moiroom.data

data class CardInfo(
    val matchingRate: Int,
    val summary: String,
    val profileImage: Int, // Drawable resource id
    val name: String,
    val location: String,
    val introduction: String
)
