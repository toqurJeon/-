package com.example.moiroom.data

import java.io.Serializable

data class CardInfo(
    val matchingRate: Int,
    val summary: String,
    val profileImage: Int, // Drawable resource id
    val name: String,
    val location: String,
    val introduction: String
) : Serializable
