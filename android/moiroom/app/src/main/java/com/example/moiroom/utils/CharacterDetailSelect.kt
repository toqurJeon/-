package com.example.moiroom.utils

import androidx.compose.ui.graphics.colorspace.Illuminant.C
import com.example.moiroom.R
import com.example.moiroom.data.CharacteristicType

fun getCharacterIcon(type: CharacteristicType): Int {
    return when (type) {
        CharacteristicType.sociability -> R.drawable.character_socialability
        CharacteristicType.positivity -> R.drawable.character_positivity
        CharacteristicType.activity -> R.drawable.character_activity
        CharacteristicType.communion -> R.drawable.character_communion
        CharacteristicType.altruism -> R.drawable.character_altruism
        CharacteristicType.empathy -> R.drawable.character_empathy
        CharacteristicType.humor -> R.drawable.character_humor
        CharacteristicType.generous -> R.drawable.character_generous

        else -> R.drawable.close_fill0_wght400_grad0_opsz24
    }
}

fun getCharacterDescription(type: CharacteristicType): String {
    return when (type) {
        CharacteristicType.sociability -> "사교에 대한 한 줄 정도의 설명."
        CharacteristicType.positivity -> "긍정에 대한 한 줄 정도의 설명."
        CharacteristicType.activity -> "활동에 대한 한 줄 정도의 설명."
        CharacteristicType.communion -> "공유에 대한 한 줄 정도의 설명."
        CharacteristicType.altruism -> "이타에 대한 한 줄 정도의 설명."
        CharacteristicType.empathy -> "공감에 대한 한 줄 정도의 설명."
        CharacteristicType.humor -> "감각에 대한 한 줄 정도의 설명."
        CharacteristicType.generous -> "관대에 대한 한 줄 정도의 설명."

        else -> "성향이 로드되지 않았습니다."
    }
}

fun getCharacterDetailDescription(type: String): String {
    return when (type) {
        "사교" -> "사교에 대한 한 줄 정도의 설명."
        "긍정" -> "긍정에 대한 한 줄 정도의 설명."
        "활동" -> "활동에 대한 한 줄 정도의 설명."
        "공유" -> "공유에 대한 한 줄 정도의 설명."
        "이타" -> "이타에 대한 한 줄 정도의 설명."
        "공감" -> "공감에 대한 한 줄 정도의 설명."
        "감각" -> "감각에 대한 한 줄 정도의 설명."
        "관대" -> "관대에 대한 한 줄 정도의 설명."

        else -> "성향이 로드되지 않았습니다."
    }
}