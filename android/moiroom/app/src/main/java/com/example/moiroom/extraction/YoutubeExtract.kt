package com.example.moiroom.extraction

import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.moiroom.R
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.AccountPicker
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope

class YoutubeExtract : AppCompatActivity() {

    private val RC_SIGN_IN = 1001 // Sign-in 요청 코드
    private lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jaeeontest)

        // Google Sign-In 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("466064898648-j9ar26egoiv5te26oj7v4j297mlmjmll")
//            .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
            .requestEmail()
            .build()

        // GoogleApiClient 초기화
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()


        signIn()
    }

    // Google Sign-In 요청
    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Google Sign-In 결과 처리
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
//            handleSignInResult(result)
//        }
//    }

    // Google Sign-In 결과 처리
    private fun handleSignInResult(result: GoogleSignInResult?) {
        if (result?.isSuccess == true) {
            val account = result.signInAccount
            if (account != null) {
                // 여기서 account를 사용하여 OAuth 2.0 토큰을 얻을 수 있습니다.
                val accessToken = account.idToken
                Log.d("AccessToken", "Token: $accessToken")
            }
        } else {
            // Google Sign-In이 실패한 경우
            Log.e("GoogleSignIn", "Failed to sign in: ${result?.status}")
        }
    }

    // Google API 클라이언트 연결 실패 처리
    fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e("GoogleApiClient", "Connection failed: ${connectionResult.errorMessage}")
    }
}
