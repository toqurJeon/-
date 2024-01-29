package com.example.moiroom

import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.Post
import com.example.moiroom.data.RequestBody
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

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
}