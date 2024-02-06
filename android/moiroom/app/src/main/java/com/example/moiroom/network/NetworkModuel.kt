import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {
    @POST("/member") // 여기를 실제 엔드포인트로 교체해주세요.
    suspend fun postUser(@Body user: User): Response<ResponseBody>

    @POST("/member/logout")
    suspend fun logoutUser(
        @Query("socialId") socialId: Long,
        @Query("provider") provider: String
    ): Response<ResponseBody>
}

object NetworkModule {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.moiroom.n-e.kr") // 여기를 실제 기본 URL로 교체해주세요.
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
