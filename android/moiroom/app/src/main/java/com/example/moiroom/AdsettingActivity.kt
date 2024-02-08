package com.example.moiroom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moiroom.databinding.ActivityAdsettingBinding
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonParser

private const val TAG = "UserInfo"

class AdsettingActivity : AppCompatActivity() {

    // 레이아웃 바인딩
    private lateinit var binding: ActivityAdsettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdsettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logoutButton = binding.logoutButton // 로그아웃 버튼
        val unregisterButton = binding.unregisterButton // 회원탈퇴 버튼

        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }

        logoutButton.setOnClickListener {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Toast.makeText(this, "사용자 정보 요청 실패 $error", Toast.LENGTH_SHORT).show()
                } else if (user != null) {
                    val userId = user.id
                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            Toast.makeText(this, "카카오 로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                        } else {
                            // 카카오 로그아웃 성공, 이제 백엔드 서버에 로그아웃 요청을 보낸다.
                            val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                            val accessToken = sharedPreferences.getString("accessToken", null)
                            if (accessToken != null) {
                                val token = "Bearer $accessToken"
                                Log.d(TAG, "토큰: $token")
                                lifecycleScope.launch {
                                    logoutUser(token, userId)
                                }
                            }
                        }
                    }
                }
            }
        }


        // 회원탈퇴 기능 구현
        unregisterButton.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    // 회원탈퇴 성공 시, 'isButtonClicked' 값을 초기화
                    val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("isButtonClicked", false)
                    editor?.apply()

                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }
    }

    suspend fun logoutUser(token : String, socialId : Long) {
        val apiService = NetworkModule.provideRetrofit(this)
        val response = withContext(Dispatchers.IO) {
            apiService.logoutUser(socialId, "kakao")
        }

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JsonParser.parseString(responseData.string()).asJsonObject
                    val message = jsonObject.get("message").asString
                    Log.d(TAG, "서버 응답: $message")
                    if (message == "로그아웃 성공") {
                        logoutSuccess()
                    } else {
                        logoutFailure(message)
                    }
                } else {
                    // 응답 본문이 null인 경우 처리
                    Log.e(TAG, "로그아웃 실패, 응답 본문이 없음")
                    logoutFailure("응답 본문이 없음")
                }
            } else {
                // 요청 실패 처리
                Log.e(TAG, "로그아웃 요청 실패, 상태 코드: ${response.code()}")
                logoutFailure("로그아웃 요청 실패, 상태 코드: ${response.code()}")
            }
        }
    }
    fun logoutSuccess() {
        Log.d(TAG, "logoutSuccess 호출됨")
        // 'isButtonClicked' 값을 초기화
        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean("isButtonClicked", false)
        editor?.apply()

        Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun logoutFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



