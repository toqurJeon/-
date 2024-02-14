package com.example.moiroom.utils

import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.Interest

fun getInterestName(type: String): String {
    return when (type) {
        "Film & Animation" -> "영화"
        "Autos & Vehicles" -> "자동차"
        "Music" -> "음악"
        "Pets & Animals" -> "동물"
        "Sports" -> "스포츠"
        "Travel & Events" -> "여행"
        "Gaming" -> "게임"
        "Videoblogging" -> "브이로그"
        "People & Blogs" -> "소셜"
        "Comedy" -> "코미디"
        "Entertainment" -> "엔터테인먼트"
        "News & Politics" -> "뉴스"
        "Howto & Style" -> "스타일"
        "Education" -> "교육"
        "Science & Technology" -> "과학"
        "Nonprofits & Activism" -> "비영리"

        else -> type
    }
}