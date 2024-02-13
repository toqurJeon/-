package com.example.moiroom.extraction

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.example.moiroom.R
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.AccountPicker
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.tasks.Task
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moiroom.GlobalApplication
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.example.moiroom.databinding.ActivityWebviewtestBinding
import com.example.moiroom.utils.getRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//import com.google.firebase.auth.GoogleAuthProvider
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.webkit.WebSettings
import com.example.moiroom.LoadingActivity
import org.json.JSONObject

class YoutubeExtract : AppCompatActivity() {
    companion object {
        var youtubeToken = ""
    }
    private val REQUEST_CODE_AUTHORIZATION = 1001 // 인증 요청 코드
    private lateinit var binding: ActivityWebviewtestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewtestBinding.inflate(layoutInflater)
//        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        getRequestResult(true, this)
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient()
//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                // 페이지가 로드되면 여기에서 URL을 확인하여 리디렉션 여부를 처리할 수 있습니다.
//                if (url?.startsWith("https://accounts.google.com/o/oauth2/auth") == true) {
//                    // 여기에서 리디렉션된 페이지에 대한 처리를 추가하세요.
//                }
//            }
//        }
        val userAgent = "moiroom" // 여기에 사용자 에이전트 문자열을 설정하세요.
        webView.settings.userAgentString = userAgent

        // 인증 페이지로 리디렉션
        val authUri ="https://accounts.google.com/o/oauth2/auth" +
                "?client_id=466064898648-d03omb79q96tgt5a94fed1u4ljnepij5.apps.googleusercontent.com" +
                "&redirect_uri=https://moiroom.firebaseapp.com/__/auth/handler" +
                "&response_type=code" +
                "&scope=https://www.googleapis.com/auth/youtube.readonly"
        webView.loadUrl(authUri)
//        val authIntent = Intent(Intent.ACTION_VIEW, authUri)
//        startActivityForResult(authIntent, REQUEST_CODE_AUTHORIZATION)

    }
    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()
            if (url.startsWith("https://moiroom.firebaseapp.com")) {
                handleRedirectUri(url)
                return true
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    private fun handleRedirectUri(url: String) {
        val uri = Uri.parse(url)
        val authCode = uri.getQueryParameter("code")
        if (authCode != null) {
            // 받은 인증 코드로 액세스 토큰 요청
            requestAccessToken(authCode)
        } else {
            Log.d("AccessToken", "Authentication code is null")
        }
    }

    private fun requestAccessToken(authCode: String) {
        Log.d("제발 토큰", "$authCode")
        // 여기서 액세스 토큰 요청을 처리합니다.
        // 인증 코드를 사용하여 액세스 토큰을 요청하고 처리하는 코드를 작성합니다.
        // 이 부분은 네트워크 요청을 통해 Google OAuth 2.0 서버에 인증 코드를 보내고 액세스 토큰을 받아오는 과정입니다.
        // 예시 코드를 통해 네트워크 요청 및 JSON 파싱 등을 수행할 수 있습니다.
        val clientId = "466064898648-d03omb79q96tgt5a94fed1u4ljnepij5.apps.googleusercontent.com"
        val clientSecret = "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"
        val redirectUri = "https://moiroom.firebaseapp.com/__/auth/handler"

        // Google OAuth 2.0 서버에 액세스 토큰을 요청하는 엔드포인트 URL
        val tokenUrl = "https://oauth2.googleapis.com/token"

        // POST 요청으로 전송할 파라미터 설정
        val postData = "code=$authCode&client_id=$clientId&client_secret=$clientSecret" +
                "&redirect_uri=$redirectUri&grant_type=authorization_code"

        // Coroutine을 사용하여 네트워크 요청 수행
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // URL 객체 생성 및 HTTP 연결 설정
                val url = URL(tokenUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.doOutput = true

                // POST 데이터 전송
                val outputStream = connection.outputStream
                outputStream.write(postData.toByteArray())
                outputStream.flush()

                // HTTP 응답 코드 확인
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 응답 본문 읽기
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuffer()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    // JSON 형식의 응답에서 액세스 토큰 추출
                    val jsonResponse = response.toString()
//                    val accessToken = jsonResponse.getJSONObject("access_token")
                    // 액세스 토큰을 사용하여 추가 작업 수행
                    Log.d("AccessToken", jsonResponse)
                    youtubeToken = jsonResponse
                    val intent = Intent(this@YoutubeExtract, LoadingActivity::class.java)
                    startActivity(intent)
                } else {
                    // HTTP 요청 실패 처리
                    Log.e("AccessToken", "HTTP 요청 실패: $responseCode")
                }
            } catch (e: Exception) {
                // 네트워크 요청 실패 처리
                Log.e("AccessToken", "네트워크 요청 실패: ${e.message}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_AUTHORIZATION && resultCode == Activity.RESULT_OK) {
            // 인증 코드 받아서 처리
            val authCode = data?.data?.getQueryParameter("code")
            if (authCode != null) {
                // 받은 인증 코드로 액세스 토큰 요청 등 추가 작업 수행
                // 여기에 작성하세요.
                Log.d("토큰", "$authCode")
            } else {
                Log.d("토큰", "토큰 null")
            }
        }
    }


}
