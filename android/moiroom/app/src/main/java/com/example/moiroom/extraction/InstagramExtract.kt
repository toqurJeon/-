package com.example.moiroom.extraction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.ApiInterface
import com.example.moiroom.GlobalApplication
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.InstagramRequest
import com.example.moiroom.data.RequestBody
import kotlinx.coroutines.launch

class InstagramExtract: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val globalApplication = application as GlobalApplication
        val apiservice = globalApplication.retrofitInstagram.create(ApiInterface::class.java)

        lifecycleScope.launch {
//            val response = apiInterface.getJaeeon()
//
//                // response가 null이 아니면 로그에 출력
//                response?.let {
//                    Log.d("결과", "Not null, POST 성공 - $it")
//                }
//            val requestBody = InstagramRequest(0.1354, 0.3159, 0.7561)
            try {
                val response = apiservice.getRequest("802744445198772", "https://example.com/instagramredirection", "user_profile,user_media", "code")
                Log.d("결과", "GET 성공: $response")
            } catch (e: Exception) {
                Log.e("에러", "GET 요청 보내기 오류", e)
            }
        }
    }


}