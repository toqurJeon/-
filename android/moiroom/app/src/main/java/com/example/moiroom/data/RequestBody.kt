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
