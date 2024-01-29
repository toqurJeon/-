package com.example.moiroom.extraction

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moiroom.ApiInterface
import com.example.moiroom.GlobalApplication
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.RequestBody
class InstagramExtract: AppCompatActivity() {
//    https://api.instagram.com/oauth/authorize?client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&scope=user_profile,user_media&response_type=code
        private suspend fun sendPostRequest(): MyResponse? {
        val globalApplication = application as GlobalApplication

        val apiService = globalApplication.retrofit2.create(ApiInterface::class.java)

        val requestBody = RequestBody(0.1354, 0.3159, 0.7561)

        return try {
            val response = apiService.postData(requestBody)
            Log.d("결과", "POST 성공 - Message: ${response.message}, Status: ${response.status}")
            response
        } catch (e: Exception) {
            Log.e("에러", "POST 요청 보내기 오류", e)
            null
        }
    }


}