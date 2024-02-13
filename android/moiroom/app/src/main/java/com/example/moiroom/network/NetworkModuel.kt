import android.content.Context
import android.util.Log
import com.example.moiroom.data.ChatRoomResponse
import com.example.moiroom.data.CityResponse
import com.example.moiroom.data.MemberInfo
//import com.example.moiroom.data.MemberResponse
import com.example.moiroom.data.Metropolitan
import com.example.moiroom.data.MetropolitanResponse
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.ResponseData
import com.example.moiroom.data.UserResponse
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.RequestBody
import com.example.moiroom.data.Member


interface ApiService {
    @POST("/member/login")
    suspend fun postUser(@Body user: User): Response<ResponseBody>

    @POST("/member/logout")
    suspend fun logoutUser(
        @Query("socialId") socialId: Long,
        @Query("provider") provider: String
    ): Response<ResponseBody>

    @GET("/area/metropolitan")
    suspend fun getMetropolitan(): Response<MetropolitanResponse>

    @GET("/area/{metropolitanId}/city")
    suspend fun getCities(@Path("metropolitanId") metropolitanId: Int): Response<CityResponse>

    @Multipart
    @PATCH("/member")
    suspend fun updateMemberInfo(
        @Part("metropolitanId") metropolitanId: RequestBody,
        @Part("cityId") cityId: RequestBody,
        @Part("memberGender") memberGender: RequestBody,
        @Part("memberNickname") memberNickname: RequestBody,
        @Part("memberIntroduction") memberIntroduction: RequestBody,
        @Part("roommateSearchStatus") roommateSearchStatus: RequestBody,
//        @Part("memberProfileImage") memberProfileImage: RequestBody,
        @Part memberProfileImage: MultipartBody.Part // 이미지 파일을 업로드하는 파라미터를 추가합니다.
    ): Response<MyResponse>

    @GET("/member")
    suspend fun getUserInfo(): Response<UserResponse>

    // 매칭 정보 리스트 조회
    @GET("matching/result")
    suspend fun getMatchedMemberList(
        @Query("pgno") pgno: Int
    ) : Response<ResponseData>

    @GET("/chat")
    suspend fun getChatRooms(@Query("pgno") pgno: Int): Response<ChatRoomResponse>

    @POST("/chat/{memberId}")
    suspend fun createChatRoom(@Path("memberId") memberId: Long): Response<ResponseBody>
}

object NetworkModule {
    private fun provideInterceptor(accessToken: String): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            if (!original.url.encodedPath.startsWith("/member/login") &&
                !original.url.encodedPath.startsWith("/member/logout")) {
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                val request = requestBuilder.build()
                Log.d("Request Info", "Authorization Header: ${request.header("Authorization")}")
                chain.proceed(request)
            } else {
                chain.proceed(original)
            }
        }
    }

    fun provideRetrofit(context: Context): ApiService {
        val sharedAccessToken = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val accessToken = sharedAccessToken.getString("accessToken", "")
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(provideInterceptor(accessToken!!))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://moiroom.n-e.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
