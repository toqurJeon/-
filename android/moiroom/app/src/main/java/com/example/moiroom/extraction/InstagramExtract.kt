package com.example.moiroom.extraction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.ApiInterface
import com.example.moiroom.GlobalApplication
import com.example.moiroom.NowMatchingActivity
import com.example.moiroom.R
import com.example.moiroom.data.InstaResponse
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.InstagramRequest
import com.example.moiroom.data.RequestBody
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.example.moiroom.databinding.ActivityWebviewtestBinding
import com.facebook.FacebookSdk.getApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
//                handleInstagramRedirect(redirectUrl)
                Log.d("허가 받음","$redirectUrl")
                val clientId = "your_client_id"
                val clientSecret = "your_client_secret"
                val redirectUri = "your_redirect_uri"
                val code = redirectUrl.substring(46)
                Log.d("코드", "$code")

                getAccessTokenWithRetrofit(clientId, clientSecret, redirectUri, code)
                val intent = Intent(this@InstagramExtract, NowMatchingActivity::class.java)
                startActivity(intent)
                return true
            } else {Log.d("엘스", "엘스")}
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    private fun handleInstagramRedirect(redirectUrl: String) {
        // 여기에서 Redirect URI 처리 및 인증 코드 추출
        // 추출한 인증 코드를 사용하여 엑세스 토큰 요청 등을 수행
        // 예: URL 파싱 등의 작업
    }

    fun getAccessTokenWithRetrofit(code: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.instagram.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)

        val call = apiService.getAccessToken(
            "802744445198772",
            "e7bbf7de75d08046d0f3c34570dfddcc",
            "authorization_code",
            "https://example.com/instagramredirection",
            code
        )

        call.enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>) {
                if (response.isSuccessful) {
                    val accessTokenResponse = response.body()
                    val accessToken = accessTokenResponse?.accessToken
                    // accessToken을 사용하여 필요한 작업을 수행합니다.
                    println("Access Token: $accessToken")
                } else {
                    println("Failed to retrieve access token.")
                }
            })

    }
}
