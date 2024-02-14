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
        CharacteristicType.sociability -> "타인과 함께하는 활동을 좋아하는 정도"
        CharacteristicType.positivity -> "낙관적으로 사고 및 행동하는 정도"
        CharacteristicType.activity -> "몸을 움직여 활발히 행동하는 정도"
        CharacteristicType.communion -> "타인과 터 놓고 이야기하는 정도"
        CharacteristicType.altruism -> "타인을 이롭게 하는 행동 및 성향의 정도"
        CharacteristicType.empathy -> "타인의 입장에서 생각해보는 능력의 정도"
        CharacteristicType.humor -> "유머를 받아 들일 수 있는 정도"
        CharacteristicType.generous -> "타인과의 관계에서 손익을 따지는 정도"

        else -> "성향이 로드되지 않았습니다."
    }
}

fun getCharacterDetailDescription(type: String): String {
    return when (type) {
        "사교" -> "사교 데이터에 대한 설명."
        "긍정" -> "긍정 데이터에 대한 설명."
        "활동" -> "활동 데이터에 대한 설명."
        "공유" -> "공유 데이터에 대한 설명."
        "이타" -> "이타 데이터에 대한 설명."
        "공감" -> "공감 데이터에 대한 설명."
        "감각" -> "감각 데이터에 대한 설명."
        "관대" -> "관대 데이터에 대한 설명."

        else -> "성향이 로드되지 않았습니다."
    }
}