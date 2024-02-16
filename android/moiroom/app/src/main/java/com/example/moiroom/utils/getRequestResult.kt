package com.example.moiroom.utils

import ApiService
import NetworkModule
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.LruCache
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import com.example.moiroom.NaviActivity
import com.example.moiroom.NowMatchingAfterFragment
import com.example.moiroom.data.Characteristic
import com.example.moiroom.data.Interest
import com.example.moiroom.data.MatchedMember
import com.example.moiroom.data.MatchedMemberData
import com.example.moiroom.data.MatchedMemberList
import com.example.moiroom.data.Member
import com.example.moiroom.data.Page
import com.example.moiroom.data.ResponseData
import com.example.moiroom.data.UserResponse
import com.example.moiroom.utils.CachedMatchingResultLiveData.addMatchingResult
import com.example.moiroom.utils.CachedPageLiveData.updatePage
import com.example.moiroom.utils.CachedUserInfoLiveData.updateUserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val cacheSize = 4 * 1024 * 1024 // 4MB

object CachedUserInfoLiveData : LiveData<UserResponse.Data.Member>() {
    val cacheUserInfo = LruCache<String, UserResponse.Data.Member>(cacheSize)

    fun updateUserInfo(userInfo: UserResponse.Data.Member) {
        cacheUserInfo.put("userInfo", userInfo)
        postValue(userInfo)
    }
}

//object CachedMatchedMemberListLiveData : LiveData<ResponseData>() {
//    val cacheMatchedMemberList = LruCache<String, ResponseData>(cacheSize)
//
//    fun updateMatchedMemberList(matchedMemberList: ResponseData) {
//        cacheMatchedMemberList.put("matchedMemberList", matchedMemberList)
//        postValue(matchedMemberList)
//    }
//}

object CachedMatchingResultLiveData : LiveData<MutableList<MatchedMemberData>>() {
    val cacheMatchingResult = LruCache<String, MutableList<MatchedMemberData>>(cacheSize)

    fun addMatchingResult(data: List<MatchedMemberData>) {
        val cachedData = cacheMatchingResult.get("matchedData") ?: mutableListOf()

        cachedData.addAll(data)
        cacheMatchingResult.put("matchedData", cachedData)

        postValue(cachedData)
    }
}

object CachedPageLiveData : LiveData<Page>() {
    val cachePage = LruCache<String, Page>(cacheSize)

    fun updatePage(data: Page) {
        cachePage.put("page", data)
        postValue(data)
    }
}

private lateinit var apiService: ApiService

fun getMatchedMember(context: Context, pgno: Int) {
    apiService = NetworkModule.provideRetrofit(context)
    // 응답 데이터 저장 (현재: 더미 데이터)
    CoroutineScope(Dispatchers.IO).launch {

        val response = apiService.getMatchedMemberList(pgno)
        if (response.isSuccessful && response.body()?.data != null) {
            val data = response.body()
            Log.d("MYTAG", "getMatchedMember: success, $data")

            // updateMatchedMemberList(data!!)
            updatePage(
                Page(
                    data!!.data.totalPages,
                    data.data.totalElements,
                    data.data.currentPage,
                    data.data.pageSize
                )
            )
            addMatchingResult(data.data.content)

            Handler(Looper.getMainLooper()).postDelayed({
                NowMatchingAfterFragment.isLoading = false
            }, 500)
        } else {
            Log.d("MYTAG", "getMatchedMember: fail, $response")

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
                                "운동 좋아! 같이 운동할 수 있는 분이 좋아요.",
                                Characteristic(
                                    6320,
                                    7052,
                                    6493,
                                    8553,
                                    5083,
                                    4910,
                                    6120,
                                    8358,
                                    "23:47",
                                    "06:32",
                                ),
                                listOf(
                                    Interest(
                                        "Sports",
                                        4800
                                    ),
                                    Interest(
                                        "Entertainment",
                                        3600
                                    ),
                                    Interest(
                                        "Science & Technology",
                                        1100
                                    ),
                                    Interest(
                                        "Gaming",
                                        500
                                    )
                                )
                            ),
                            9800,
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
                                    6120,
                                    7252,
                                    6193,
                                    8053,
                                    5283,
                                    5510,
                                    6320,
                                    8058,
                                    "23:47",
                                    "06:32",
                                ),
                                listOf(
                                    Interest(
                                        "Music",
                                        6000
                                    ),
                                    Interest(
                                        "Film & Animation",
                                        2500
                                    ),
                                    Interest(
                                        "Travel & Events",
                                        1500
                                    )
                                )
                            ),
                            9000,
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
                                "ㅎㅇㅎㅇ 안녕하세요~~",
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
                                        "Music",
                                        6000
                                    ),
                                    Interest(
                                        "Comedy",
                                        2500
                                    ),
                                    Interest(
                                        "Education",
                                        1500
                                    )
                                )
                            ),
                            8800,
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
                                        "Film & Animation",
                                        6000
                                    ),
                                    Interest(
                                        "Travel & Events",
                                        2500
                                    ),
                                    Interest(
                                        "Entertainment",
                                        1500
                                    )
                                )
                            ),
                            8400,
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
                                        "Howto & Style",
                                        6000
                                    ),
                                    Interest(
                                        "Nonprofits & Activism",
                                        2500
                                    ),
                                    Interest(
                                        "Film & Animation",
                                        1500
                                    )
                                )
                            ),
                            8100,
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
                                        "Film & Animation",
                                        6000
                                    ),
                                    Interest(
                                        "Autos & Vehicles",
                                        2500
                                    ),
                                    Interest(
                                        "Gaming",
                                        1500
                                    )
                                )
                            ),
                            7900,
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
                                        "Entertainment",
                                        6000
                                    ),
                                    Interest(
                                        "News & Politics",
                                        2500
                                    ),
                                    Interest(
                                        "Autos & Vehicles",
                                        1500
                                    )
                                )
                            ),
                            7700,
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
                                        "Sports",
                                        6000
                                    ),
                                    Interest(
                                        "Film & Animation",
                                        2500
                                    ),
                                    Interest(
                                        "Entertainment",
                                        1500
                                    )
                                )
                            ),
                            7500,
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
                                        "Film & Animation",
                                        6000
                                    ),
                                    Interest(
                                        "Music",
                                        2500
                                    ),
                                    Interest(
                                        "Travel & Events",
                                        1500
                                    )
                                )
                            ),
                            7100,
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
                                        "Music",
                                        6000
                                    ),
                                    Interest(
                                        "Nonprofits & Activism",
                                        2500
                                    ),
                                    Interest(
                                        "Travel & Events",
                                        1500
                                    )
                                )
                            ),
                            6500,
                            "취미가 잘 맞아요!"
                        )
                    ),
                    1,
                    10,
                    1,
                    10
                ),
            )
            updatePage(
                Page(
                    responseMatchedMemberList.data.totalPages,
                    responseMatchedMemberList.data.totalElements,
                    responseMatchedMemberList.data.currentPage,
                    responseMatchedMemberList.data.pageSize
                )
            )
            addMatchingResult(responseMatchedMemberList.data.content)
            // updateMatchedMemberList(responseMatchedMemberList)
        }
    }
}

fun getUserInfo(context: Context) {
    apiService = NetworkModule.provideRetrofit(context)

    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = apiService.getUserInfo()
            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                val member = userResponse.data.member
                Log.d("TAG","${response.body()}")

                updateUserInfo(member)

                Log.d("MYTAG", "getUserInfo: User info saved in cache. memberId: ${member.memberId}, memberName: ${member.memberName}, memberNickname: ${member.memberNickname}")
                Log.d("MYTAG", "유저 데이터 가져옴, 관심사 ${member.interests}")
            } else {
                Log.d("MYTAG", "getUserInfo: Failed to get user info")
                Log.d("TAG", "Response Code: ${response.code()}, Response Message: ${response.message()}")
                response.errorBody()?.let {
                    Log.d("TAG", "Error Body: ${it.string()}")
                }

                val responseUserInfo: UserResponse =
                    UserResponse(
                        200,
                        "No Ok",
                        "더미 데이터 불러오기",
                        UserResponse.Data(
                            UserResponse.Data.Member(
                                1,
                                "https://images.dog.ceo/breeds/samoyed/n02111889_6249.jpg",
                                "안드리아",
                                "female",
                                "김민지",
                                "1999",
                                "서울특별시",
                                "강남구",
                                "멍멍이를 엄청 좋아해요. 댕댕.",
                                1,
                                Characteristic(
                                    6520,
                                    7552,
                                    6993,
                                    7653,
                                    5683,
                                    4210,
                                    6020,
                                    8758,
                                    "23:47",
                                    "06:32",
                                ),
                                listOf(
                                    Interest(
                                        "Sports",
                                        4200
                                    ),
                                    Interest(
                                        "Music",
                                        2800
                                    ),
                                    Interest(
                                        "Gaming",
                                        1900
                                    ),
                                    Interest(
                                        "Film & Animation",
                                        600
                                    ),
                                    Interest(
                                        "Science & Technology",
                                        500
                                    )
                                )
                            )
                        )
                    )

                // 메모리 캐시에 저장
                updateUserInfo(responseUserInfo.data.member)
            }
        } catch (e: Exception) {
            Log.e("MYTAG", "getUserInfo: Error", e)
            e.printStackTrace()

            val responseUserInfo: UserResponse =
                UserResponse(
                    200,
                    "No Ok",
                    "더미 데이터 불러오기",
                    UserResponse.Data(
                        UserResponse.Data.Member(
                            1,
                            "https://images.dog.ceo/breeds/samoyed/n02111889_6249.jpg",
                            "안드리아",
                            "female",
                            "김민지",
                            "1999",
                            "서울특별시",
                            "강남구",
                            "멍멍이를 엄청 좋아해요. 댕댕.",
                            1,
                            Characteristic(
                                6520,
                                7552,
                                6993,
                                7653,
                                5683,
                                4210,
                                6020,
                                8758,
                                "23:47",
                                "06:32",
                            ),
                            listOf(
                                Interest(
                                    "Sports",
                                    4200
                                ),
                                Interest(
                                    "Music",
                                    2800
                                ),
                                Interest(
                                    "Gaming",
                                    1900
                                ),
                                Interest(
                                    "Film & Animation",
                                    600
                                ),
                                Interest(
                                    "Science & Technology",
                                    500
                                )
                            )
                        )
                    )
                )

            // 메모리 캐시에 저장
            updateUserInfo(responseUserInfo.data.member)
        }
    }
}
