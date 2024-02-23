package com.example.moiroom.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.moiroom.R

private val interestColorMap = mapOf(
    "Film & Animation" to R.color.pastel_green_2,
    "Autos & Vehicles" to R.color.pastel_red,
    "Music" to R.color.pastel_yellow,
    "Pets & Animals" to R.color.pastel_blue_2,
    "Sports" to R.color.pastel_blue,
    "Travel & Events" to R.color.pastel_purple_2,
    "Gaming" to R.color.pastel_aqua_green,
    "Videoblogging" to R.color.pastel_sky_blue,
    "People & Blogs" to R.color.pastel_orange,
    "Comedy" to R.color.pastel_yellow_2,
    "Entertainment" to R.color.pastel_pink_2,
    "News & Politics" to R.color.pastel_purple,
    "Howto & Style" to R.color.pastel_pink,
    "Education" to R.color.pastel_sky_blue_2,
    "Science & Technology" to R.color.pastel_green,
    "Nonprofits & Activism" to R.color.pastel_orange_2
)

fun getColorInterest(interestName: String,  context: Context): Int {
    val colorResId = interestColorMap[interestName]
    return colorResId?.let { ContextCompat.getColor(context, it) } ?: getRandomColor()
}

private val characterBGColorMap = mapOf(
    "사교" to R.color.character_bg_sociable,
    "긍정" to R.color.character_bg_positivity,
    "활동" to R.color.character_bg_activity,
    "공유" to R.color.character_bg_communion,
    "이타" to R.color.character_bg_altruism,
    "공감" to R.color.character_bg_empathy,
    "감각" to R.color.character_bg_humor,
    "관대" to R.color.character_bg_generous,
    "default" to R.color.gray_high_brightness_background,
)

private val characterColorMap = mapOf(
    "사교" to R.color.character_sociable,
    "긍정" to R.color.character_positivity,
    "활동" to R.color.character_activity,
    "공유" to R.color.character_communion,
    "이타" to R.color.character_altruism,
    "공감" to R.color.character_empathy,
    "감각" to R.color.character_humor,
    "관대" to R.color.character_generous,
    "default" to R.color.gray_high_brightness_background,
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
