import android.content.Context
import com.example.moiroom.data.CityResponse
import com.example.moiroom.data.Metropolitan
import com.example.moiroom.data.MetropolitanResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getCities(@Path("id") metropolitanId: Int): Response<CityResponse>
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
            .baseUrl("http://i10a308.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
