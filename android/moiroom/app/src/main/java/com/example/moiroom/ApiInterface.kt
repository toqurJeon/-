package com.example.moiroom

import com.example.moiroom.data.InstagramRequest
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.RequestBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @GET("posts")
    suspend fun getPosts(): List<Post>

//    @GET("posts/{id}")
//    suspend fun getPostDetail(@Path("id") postId: Int): Call<Post>

    @GET("posts")
    suspend fun getAnother() : List<Post>
    // suspend는 코루틴과 함께 사용
    // 코루틴을 사용해서 메인 스레드에서 작업하지 않고 백그라운드에서 작업하도록 설정

    @POST("receive_and_send")
    suspend fun postData(@Body requestBody: RequestBody): MyResponse

    @POST("receive_and_send")
    suspend fun postData2(@Body requestBody: InstagramRequest): MyResponse


    @GET
    suspend fun getJaeeon(): String

    @GET("authorize/")
    fun getRequest(@Query("client_id") cliend_id: String, @Query("redirect_uri") redirect_uri: String, @Query("scope") scope: String, @Query("response_type") response_type: String): Call<InstagramRequest>
}
