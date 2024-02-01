import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @POST("/member") // 여기를 실제 엔드포인트로 교체해주세요.
    suspend fun postUser(@Body user: User): Response<ResponseBody>
}

object NetworkModule {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://i10a308.p.ssafy.io:8080") // 여기를 실제 기본 URL로 교체해주세요.
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
