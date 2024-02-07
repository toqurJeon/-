package com.example.moiroom

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//성현
import android.util.Log
import com.kakao.sdk.common.util.Utility

//성현
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import fetchUserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// AppCompatActivity: AndroidX에서 제공하는 액티비티 클래스
class MainActivity : AppCompatActivity() {
    // 매개변수 savedInstanceState: 액티비티가 프래그먼트가 다시 생성되었을 때 이전에 저장된 상태
    // Bundle?: 데이터 형태가 Bundle 객체이고 null일 수도 있음
    // Bundle객체: 데이터를 전달하고 받을 때 사용하는 컨테이너 클래스. 키-쌍 형태로 데이터를 저장
    override fun onCreate(savedInstanceState: Bundle?) {
        // 매개변수 savedInstanceState를 이용해서 부모 클래스에서 상속받은 onCreate 함수 실행
        super.onCreate(savedInstanceState)
        // res/layout/activity_login.xml 파일을 화면에 띄움
        // 수정 필요!!! 카카오 토큰이 있으면 바로 activity_navi로 가도록 수정 필요
        setContentView(R.layout.activity_login)
        // 로그인 정보 확인(성현)
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            // 토큰 정보가 없어서 에러가 나면 토스트 띄운 후 현재 화면 유지
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            // 토큰 정보가 있으면 토스트 띄운 후 NaviActivity로 인텐트 보내기
            else if (tokenInfo != null) {

                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()

                val sharedPreferences = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)
                val intent: Intent

                // '매칭 시작하기' 버튼을 한 번이라도 클릭했다면 NaviActivity로 이동
                if (isButtonClicked) {
                    intent = Intent(this, NaviActivity::class.java)
                } else {
                    // '매칭 시작하기' 버튼을 한 번도 클릭하지 않았다면 InfoinputActivity로 이동
                    intent = Intent(this, InfoinputActivity::class.java)
                }

                // 스택에 쌓여있던 액티비티 모두 제거하고 액티비티 시작
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                // 현재 실행 중인 액티비티 종료
                finish()
            }

        }

        // 키 해시 값을 구하기 위한 코드(성현)
        val sharedPreferences = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        // 카카오 로그인 함수
        // 0AuthToken, Throwable 2개의 변수를 받을 수 있는, 반환값이 없는(Unit) 함수
        // 0AuthToken - token, Throwable - error 연결
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            // 에러가 있으면 토스트 띄우기
            if (error != null) {
                // 에러가 아래 옵션 중에 있으면 특정 토스트 띄우기
                when {
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    // 에러가 위 옵션 중에 없으면 '기타 에러' 토스트 띄우기'
                    else -> {
                        Log.e("Kakao Login Error", error?.message ?: "Unknown error")
                        error.printStackTrace()
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // 토큰이 있으면 토스트 띄우고 InfoinputActivity로 인텐트 보내기
            else if (token != null) {

                val accessToken = token.accessToken
                val refreshToken = token.refreshToken

                // 액세스 토큰을 SharedPreferences에 저장
                val sharedAccessToken = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                val editor = sharedAccessToken.edit()
                editor.putString("accessToken", accessToken)
                editor.apply()

                System.out.println("Access Token: $accessToken")
                System.out.println("Refresh Token: $refreshToken")
                Log.d("KaKaoAccessToken", "Access Token: $accessToken")
                Log.d("KaKaoRefreshToken", "Refresh Token: $refreshToken")

                // 사용자 정보를 가져옵니다.
                fetchUserInfo(this, accessToken, refreshToken)

                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val sharedPreferences = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)
                val intent: Intent

                // '매칭 시작하기' 버튼을 한 번이라도 클릭했다면 NaviActivity로 이동
                if (isButtonClicked) {
                    intent = Intent(this, NaviActivity::class.java)
                } else {
                    // '매칭 시작하기' 버튼을 한 번도 클릭하지 않았다면 InfoinputActivity로 이동
                    intent = Intent(this, InfoinputActivity::class.java)
                }

                // 스택에 쌓여있던 액티비티 모두 제거하고 액티비티 시작
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                // 현재 실행 중인 액티비티 종료
                finish()
            }
        }

        // 로그인 버튼(성현)
        val kakao_login_button = findViewById<ImageButton>(R.id.kakao_login_button)

        // 로그인 버튼에 클릭 이벤트 생성
        kakao_login_button.setOnClickListener {
            // 카카오톡 로그인이 현재 디바이스에서 가능하면
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                // 카카오톡으로 로그인
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }
            // 카카오톡 로그인이 현재 디바이스에서 불가능하면, 카카오 계정으로 로그인 유도
            else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}