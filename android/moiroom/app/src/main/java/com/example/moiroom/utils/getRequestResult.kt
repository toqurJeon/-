package com.example.moiroom.utils

import ApiService
import NetworkModule
import android.content.Context
import android.util.Log
import android.util.LruCache
import com.example.moiroom.data.Characteristic
import com.example.moiroom.data.Interest
import com.example.moiroom.data.MatchedMember
import com.example.moiroom.data.MatchedMemberData
import com.example.moiroom.data.MatchedMemberList
import com.example.moiroom.data.Member
import com.example.moiroom.data.ResponseData
import com.example.moiroom.data.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val cacheSize = 4 * 1024 * 1024 // 4MB
val cacheUserInfo = LruCache<String, UserResponse.Data.Member>(cacheSize)
val cacheMatchedMemberList = LruCache<String, ResponseData>(cacheSize)

private lateinit var apiService: ApiService

var okCount: Int = 0

fun getRequestResult(result: Boolean, context: Context) {
    // ok response를 받아야 하는 갯수
    val targetCount: Int = 1
    Log.d("TAG", "getRequestResult: $result 응답 받음! okCount: $okCount")

    if (result) {
        okCount += 1
        if (okCount == targetCount) {
            // 매칭 리스트 GET 요청
            Log.d("TAG", "getRequestResult: 매칭리스트 요청 보내기!")

            getUserInfo(context)
            getMatchedMember(context, 1)
        }
    }
}

fun getMatchedMember(context: Context, pgno: Int) {
    apiService = NetworkModule.provideRetrofit(context)
    // 응답 데이터 저장 (현재: 더미 데이터)
    CoroutineScope(Dispatchers.IO).launch {

        val response = apiService.getMatchedMemberList(1)
        if (response.isSuccessful) {
            val data = response.body()
            Log.d("MYTAG", "getMatchedMember: $data")
        } else {
            Log.d("MYTAG", "getMatchedMember: $response")
        }
    }

    val responseMatchedMemberList: ResponseData = ResponseData(
        200,
        "OK",
        "추천 룸메이트 조회 성공",
        MatchedMemberList(
            listOf(
                MatchedMemberData(
                    MatchedMember(
                        2,
                        "https://images.dog.ceo/breeds/labrador/n02099712_7866.jpg",
                        "모이루미",
                        "여자",
                        2000,
                        "서울특별시",
                        "강남구",
                        "운동을 좋아해서 같이 운동할 수 있는 분이 좋아요.",
                        Characteristic(
                            5123,
                            6656,
                            8634,
                            4156,
                            3223,
                            1278,
                            6723,
                            3534,
                            "23:40",
                            "06:30",
                        ),
                        listOf(
                            Interest(
                                "운동",
                                48
                            ),
                            Interest(
                                "음악",
                                36
                            ),
                            Interest(
                                "요리",
                                11
                            ),
                            Interest(
                                "게임",
                                5
                            )
                        )
                    ),
                    98,
                    "비슷한 생활루틴을 가졌어요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        3,
                        "https://images.dog.ceo/breeds/pembroke/n02113023_7275.jpg",
                        "모이라",
                        "여자",
                        1998,
                        "서울특별시",
                        "강남구",
                        "반가워요. 잘 살고 싶어요.",
                        Characteristic(
                            4423,
                            8156,
                            8034,
                            2456,
                            1187,
                            7545,
                            3634,
                            3565,
                            "00:10",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    90,
                    "취미가 비슷해요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        4,
                        "https://images.dog.ceo/breeds/malamute/n02110063_15580.jpg",
                        "모바모바일",
                        "여자",
                        1998,
                        "서울특별시",
                        "강남구",
                        "ㅎㅇㅎㅇ~~",
                        Characteristic(
                            5423,
                            8956,
                            7934,
                            6456,
                            5187,
                            6545,
                            6634,
                            2565,
                            "00:10",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    88,
                    "취미가 비슷해요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        5,
                        "https://images.dog.ceo/breeds/samoyed/n02111889_6625.jpg",
                        "모란드",
                        "여자",
                        1996,
                        "서울특별시",
                        "강남구",
                        "안녕하세요!!",
                        Characteristic(
                            6423,
                            7956,
                            7034,
                            7456,
                            7187,
                            5545,
                            6934,
                            6565,
                            "00:30",
                            "07:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    84,
                    "잘 맞을 것 같은데요???!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        6,
                        "https://images.dog.ceo/breeds/komondor/n02105505_515.jpg",
                        "모드리치",
                        "여자",
                        1992,
                        "서울특별시",
                        "강남구",
                        "모이룸조아",
                        Characteristic(
                            5823,
                            8256,
                            4934,
                            3456,
                            7187,
                            5545,
                            7634,
                            4565,
                            "00:20",
                            "08:10",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    81,
                    "취미가 비슷해요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        7,
                        "https://images.dog.ceo/breeds/havanese/00100trPORTRAIT_00100_BURST20191112123933390_COVER.jpg",
                        "모라이스",
                        "여자",
                        2000,
                        "서울특별시",
                        "강남구",
                        "여행을 아주 좋아합니다",
                        Characteristic(
                            4423,
                            6956,
                            4934,
                            8456,
                            7187,
                            6545,
                            7334,
                            4565,
                            "00:04",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    79,
                    "취미가 비슷해요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        8,
                        "https://images.dog.ceo/breeds/sheepdog-english/n02105641_6875.jpg",
                        "모버릭",
                        "여자",
                        1999,
                        "서울특별시",
                        "강남구",
                        "룸메 찾는 중이여요",
                        Characteristic(
                            4423,
                            7956,
                            7734,
                            5456,
                            5587,
                            5545,
                            6634,
                            3565,
                            "00:10",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    77,
                    "취미가 비슷해요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        9,
                        "https://images.dog.ceo/breeds/ovcharka-caucasian/IMG_20190628_144843.jpg",
                        "모소리",
                        "여자",
                        2001,
                        "서울특별시",
                        "강남구",
                        "안녀하에여",
                        Characteristic(
                            5423,
                            8956,
                            7934,
                            6456,
                            5187,
                            6545,
                            6634,
                            2565,
                            "00:10",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    75,
                    "취미가 비슷해요!",
                ),
                MatchedMemberData(
                    MatchedMember(
                        10,
                        "https://images.dog.ceo/breeds/finnish-lapphund/mochilamvan.jpg",
                        "모빌리티",
                        "여자",
                        1998,
                        "서울특별시",
                        "강남구",
                        "하이용",
                        Characteristic(
                            5423,
                            8956,
                            7934,
                            6456,
                            5187,
                            6545,
                            6634,
                            2565,
                            "00:10",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    71,
                    "취미가 잘 맞아요!"
                ),
                MatchedMemberData(
                    MatchedMember(
                        11,
                        "https://images.dog.ceo/breeds/sheepdog-shetland/n02105855_5880.jpg",
                        "모잉쥐",
                        "여자",
                        1999,
                        "서울특별시",
                        "강남구",
                        "모 이 룸 최 고",
                        Characteristic(
                            5423,
                            8956,
                            7934,
                            6456,
                            5187,
                            6545,
                            6634,
                            2565,
                            "00:10",
                            "08:38",
                        ),
                        listOf(
                            Interest(
                                "음악",
                                60
                            ),
                            Interest(
                                "그림",
                                25
                            ),
                            Interest(
                                "여행",
                                15
                            )
                        )
                    ),
                    65,
                    "취미가 잘 맞아요!"
                )
            ),
            1,
            10,
            1,
            10
            ),
        )
    // 메모리 캐시에 저장
    cacheMatchedMemberList.put("matchedMemberList", responseMatchedMemberList)
}

//fun getUserInfo() {
//    // 본인 정보 GET 요청
//
//    // 응답 데이터 저장 (현재: 더미 데이터)
//    val responseUserInfo: Member = Member(
//        1,
//        "https://images.dog.ceo/breeds/samoyed/n02111889_6249.jpg",
//        "안드리아",
//        "여자",
//        "김민식",
//        1999,
//        "서울특별시",
//        "강남구",
//        "멍멍이를 엄청 좋아해요. 댕댕.",
//        1,
//        Characteristic(
//            6520,
//            7552,
//            6993,
//            7653,
//            5683,
//            4210,
//            6020,
//            8758,
//            "23:47",
//            "06:32",
//        ),
//        listOf(
//            Interest(
//                "운동",
//                48
//            ),
//            Interest(
//                "음악",
//                36
//            ),
//            Interest(
//                "요리",
//                11
//            ),
//            Interest(
//                "게임",
//                5
//            )
//        )
//    )
////    // 메모리 캐시에 저장
//    cacheUserInfo.put("userInfo", responseUserInfo)
//}

fun getUserInfo(context: Context) {
    apiService = NetworkModule.provideRetrofit(context)
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = apiService.getUserInfo()
            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                val member = userResponse.data.member
                Log.d("TAG","${response.body()}")
                cacheUserInfo.put("userInfo", member)
                Log.d("TAG", "getUserInfo: User info saved in cache. memberId: ${member.memberId}, memberName: ${member.memberName}")
            } else {
                Log.d("TAG", "getUserInfo: Failed to get user info")
                Log.d("TAG", "Response Code: ${response.code()}, Response Message: ${response.message()}")
                response.errorBody()?.let {
                    Log.d("TAG", "Error Body: ${it.string()}")
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "getUserInfo: Error", e)
            e.printStackTrace()
        }
    }
}





