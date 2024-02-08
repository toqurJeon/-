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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.google.firebase.auth.GoogleAuthProvider

class YoutubeExtract : AppCompatActivity() {

//    private val RC_SIGN_IN = 1001 // Sign-in 요청 코드
//    private lateinit var mGoogleApiClient: GoogleApiClient
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("시작", "시작")
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_jaeeontest)
//
//        // Google Sign-In 옵션 설정
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("389148661924-jv1mk55jr7c1ti4tet7n8qbb1dij6ts0.apps.googleusercontent.com")
//            .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
//            .requestEmail()
//            .build()
//        Log.d("권한 화면", "권한 화면")
//        // GoogleApiClient 초기화
//        mGoogleApiClient = GoogleApiClient.Builder(this)
//            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            onActivityResult(RC_SIGN_IN, result.resultCode, result.data)
//        }
//
//        signIn()
//    }
//
//    // Google Sign-In 요청
//    private fun signIn() {
//        Log.d("싸인인", "싸인인")
////        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
////        startActivityForResult(signInIntent, RC_SIGN_IN)
//        val signInIntent = googleSignInClient.signInIntent
//        signInLauncher.launch(signInIntent)
//    }
//
//    // Google Sign-In 결과 처리
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN && data != null ) {
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            Log.d("테스크", "$task")
////            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
////            val account = task.getResult(ApiException::class.java)
////            Log.d("어카운트", "$account")
////            val accessToken = account?.idToken
//            handleSignInResult(task)
//        }
//    }
//
//    // Google Sign-In 결과 처리
//    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
//        Log.d("핸들", "${task.isSuccessful}")
//        try {
//            val account = task.getResult(ApiException::class.java)
//            val accessToken = account?.idToken
//            Log.d("토큰", "$accessToken")
//            // 액세스 토큰을 사용하여 추가 작업 수행
//            if (accessToken != null) {
//                // 액세스 토큰을 이용한 작업 수행
//            } else {
//                // 액세스 토큰이 null인 경우 처리
//            }
//        } catch (e: ApiException) {
//            Log.d("에러", "$e")
//            // 로그인 실패 처리
//            // e.statusCode 및 e.message 등을 통해 실패 상세 정보 확인 가능
//        }
//    }
//
//    // Google API 클라이언트 연결 실패 처리
//    fun onConnectionFailed(connectionResult: ConnectionResult) {
//        Log.e("GoogleApiClient", "Connection failed: ${connectionResult.errorMessage}")
//    }
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private val RC_SIGN_IN = 1001 // Sign-in 요청 코드
//
//    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        Log.d("토큰", "${result}")
//        if (result.resultCode == RESULT_OK) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            val account = task.result
//            val idToken = account?.idToken
//
//            if (idToken != null) {
//                // 유효한 ID 토큰이 있으면 유효성 검사 및 액세스 토큰 요청
//                requestAccessToken(idToken)
//            } else {
//                // ID 토큰이 없음
//            }
//        } else {
//            // 로그인 실패
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("389148661924-jv1mk55jr7c1ti4tet7n8qbb1dij6ts0.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        signIn()
//
//    }
//
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        signInLauncher.launch(signInIntent)
//    }
//
//    private fun requestAccessToken(idToken: String) {
//        // ID 토큰을 사용하여 서버에서 액세스 토큰을 요청하는 코드
//        // 이 부분은 앱에서 직접 서버에 요청하여 구현해야 함
//        // 해당 로직은 서버 측에서 구현하도록 해야 함
//        // 서버에서 유효한 ID 토큰을 검증하고 액세스 토큰을 발급해야 함
//        // 앱은 이 액세스 토큰을 사용하여 API 요청을 보낼 수 있음
//        // 서버 측에서 이 부분을 구현하지 않으면 액세스 토큰을 가져올 수 없음
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("시작", "시작")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jaeeontest)

        // Google Sign-In 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("466064898648-d03omb79q96tgt5a94fed1u4ljnepij5.apps.googleusercontent.com")
            .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
            .requestEmail()
            .build()
        Log.d("권한 화면", "권한 화면")


        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리...........................

            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (task != null ) {
                Log.d("널?", "널 아님")
            } else {
                Log.d("널?", "널널")
            }
            Log.d("결과", "$task")
            try {
                val account = task.getResult(ApiException::class.java)!!

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                Log.d("결과2", "$credential")
                GlobalApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            Log.d("성공?", "${account.email}")
                            GlobalApplication.email = account.email

//                            changeVisibility("login")
                        }else {
//                            changeVisibility("logout")
                        }
                    }
            }catch (e: ApiException){
                Log.d("에러", "$e")
//                changeVisibility("logout")
            }
        }
    val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
    requestLauncher.launch(signInIntent)
    }
}
