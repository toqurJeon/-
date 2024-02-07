package com.example.moiroom.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.moiroom.R

private val interestColorMap = mapOf(
    "운동" to R.color.pastelBlue,
    "음악" to R.color.pastelGreen,
    "요리" to R.color.pastelYellow,
    "게임" to R.color.pastelPurple,
    "그림" to R.color.pastelOrange,
    "여행" to R.color.pastelPink,
)

fun getColorInterest(interestName: String,  context: Context): Int {
    val colorResId = interestColorMap[interestName]
    return colorResId?.let { ContextCompat.getColor(context, it) } ?: getRandomColor()
}

private val characterBGColorMap = mapOf(
    "사교" to R.color.bg_socialable,
    "긍정" to R.color.bg_positivity,
    "활동" to R.color.bg_activity,
    "공유" to R.color.bg_communion,
    "이타" to R.color.bg_altruism,
    "공감" to R.color.bg_empathy,
    "감각" to R.color.bg_humor,
    "관대" to R.color.bg_generous,
    "default" to R.color.BGgray,
)

private val characterColorMap = mapOf(
    "사교" to R.color.socialable,
    "긍정" to R.color.positivity,
    "활동" to R.color.activity,
    "공유" to R.color.communion,
    "이타" to R.color.altruism,
    "공감" to R.color.empathy,
    "감각" to R.color.humor,
    "관대" to R.color.generous,
    "default" to R.color.BGgray,
)

fun getBGColorCharacter(characterName: String,  context: Context): Int {
    val colorResId = characterBGColorMap[characterName]
    return colorResId?.let { ContextCompat.getColor(context, it) } ?: getRandomColor()
}

fun getColorCharacter(characterName: String,  context: Context): Int {
    val colorResId = characterColorMap[characterName]
    return colorResId?.let { ContextCompat.getColor(context, it) } ?: getRandomColor()
}

fun getRandomColor(): Int {
    val r = (0..255).random()
    val g = (0..255).random()
    val b = (0..255).random()
    return Color.rgb(r, g, b)
}
