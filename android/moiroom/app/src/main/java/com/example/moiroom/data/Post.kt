package com.example.moiroom.data

import kotlinx.serialization.Serializable

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Serializable
data class MyResponse2(val latitude: String, val longitude: String)