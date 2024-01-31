package com.example.moiroom.extraction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.moiroom.NowMatchingActivity
import com.example.moiroom.R
import com.example.moiroom.databinding.ActivityWebviewtestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InstagramExtract: AppCompatActivity() {
    private lateinit var binding: ActivityWebviewtestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewtestBinding.inflate(layoutInflater)
//        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = InstagramAuthWebViewClient()
        val url = "https://api.instagram.com/oauth/authorize?client_id=802744445198772&redirect_uri=https://example.com/instagramredirection&scope=user_profile,user_media&response_type=code"
        webView.loadUrl(url)
    }
    private inner class InstagramAuthWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            // Redirect URI를 캐치하여 처리
            val redirectUrl = request?.url.toString()
            if (redirectUrl.startsWith("https://example.com/instagramredirection?code=")) {
                // 여기에서 Redirect URI 처리 및 인증 코드 추출
                // 추출한 인증 코드를 사용하여 엑세스 토큰 요청 등을 수행
                Log.d("허가 받음","$redirectUrl")
                val code = redirectUrl.substring(46)
                Log.d("코드", "$code")
                postFuel(code)
                val intent = Intent(this@InstagramExtract, NowMatchingActivity::class.java)
                startActivity(intent)
                return true
            } else {Log.d("엘스", "엘스")}
            // else 부분은 추가로 구현해야할 듯
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    fun postFuel(code: String) {
        // FuelManager 설정 (선택사항)
        FuelManager.instance.basePath = "https://api.instagram.com"

        val clientId = "802744445198772"
        val clientSecret = "e7bbf7de75d08046d0f3c34570dfddcc"
        val grantType = "authorization_code"
        val redirectUri = "https://example.com/instagramredirection" // 실제 콜백 URL로 변경해야 함
        val requestCode = code

        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/oauth/access_token")
                    .header("Content-Type" to "application/x-www-form-urlencoded")
                    .body(
                        "client_id=$clientId&" +
                                "client_secret=$clientSecret&" +
                                "grant_type=$grantType&" +
                                "redirect_uri=$redirectUri&" +
                                "code=$requestCode"
                    )
                    .responseString()

                // 응답 확인
                response.third.fold(
                    success = { data ->
                        sendInstagramAccessToken(data)
                    },
                    failure = { error -> Log.d("에러", "에러: $error") }
                )
            } catch (e: Exception) {
                println("에러: $e")
            }
        }
    }

    fun sendInstagramAccessToken(res: String) {
        Log.d("전달", "어세스토큰")
        // FuelManager 설정 (선택사항)

        // 경로 바꾸기
        FuelManager.instance.basePath = "https://api.instagram.com"


        val gson = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val instaMap: Map<String, String> = gson.fromJson(res, type)

        Log.d("전달 정보", "$instaMap")

        val accessToken = instaMap.get("access_token")
        val userId = instaMap.get("user_id")
        Log.d("전달 정보", "${accessToken}, ${userId}")

        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/oauth/access_token") // 경로 바꾸기
                    .header("Content-Type" to "application/x-www-form-urlencoded")
                    .body(
                        "accessToken=$accessToken&" + "user_id=$userId&"
                    )
                    .responseString()

                // 응답 확인
                response.third.fold(
                    success = { data -> Log.d("성공", "$data") },
                    failure = { error -> Log.d("에러", "에러: $error") }
                )
            } catch (e: Exception) {
                Log.d("에러", "트라이 에러")
            }
        }
    }
}
