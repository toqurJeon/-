package com.example.moiroom.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.moiroom.R

private val interestColorMap = mapOf(
    "운동" to R.color.pastelBlue,
    "음악" to R.color.pastelOrange,
    "요리" to R.color.pastelYellow,
)

fun getColorInterest(interestName: String,  context: Context): Int {
    val colorResId = interestColorMap[interestName]
    return colorResId?.let { ContextCompat.getColor(context, it) } ?: getRandomColor()
}

fun getRandomColor(): Int {
    val r = (0..255).random()
    val g = (0..255).random()
    val b = (0..255).random()
    return Color.rgb(r, g, b)
}
