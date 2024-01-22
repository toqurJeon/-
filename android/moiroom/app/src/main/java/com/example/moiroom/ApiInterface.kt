package com.example.moiroom

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface ApiInterface {
    @GET("posts")
    suspend fun getPosts(): List<Post>

//    @GET("posts/{id}")
//    suspend fun getPostDetail(@Path("id") postId: Int): Call<Post>

    @GET("posts")
    suspend fun getAnother() : List<Post>
    // suspend는 코루틴과 함께 사용
    // 코루틴을 사용해서 메인 스레드에서 작업하지 않고 백그라운드에서 작업하도록 설정
}