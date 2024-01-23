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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 로그인 정보 확인(성현)
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()

                // SecondActivity 대신 InfoInputActivity로 변경
                val intent = Intent(this, NowMatchingActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        // 키 해시 값을 구하기 위한 코드(성현)
        val sharedPreferences = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
        Log.d("Button Clicked", "Button clicked: $isButtonClicked")

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
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
                    else -> { // Unknown
                        Log.e("Kakao Login Error", error?.message ?: "Unknown error")
                        error.printStackTrace()
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InfoinputActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        val kakao_login_button = findViewById<ImageButton>(R.id.kakao_login_button) // 로그인 버튼(성현)

        kakao_login_button.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}

//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_jaeeontest)
//        Log.d("PhotoPath", "시작")
//        // 갤러리에서 모든 사진 가져오기
//        val photos = getAllPhotos()
//        Log.d("PhotoPath", "${photos.size}")
//        // 가져온 사진들에 대한 처리
//        for (photo in photos) {
//            // 각 사진에 대한 처리를 여기에 추가
////            val textView = findViewById<TextView>(R.id.textView)
//            val myImageView = findViewById<ImageView>(R.id.textView)
//            // TextView에 데이터 설정
//            Glide.with(this)
//                .load(photo)
//                .into(myImageView)
//            Log.d("PhotoPath", "Photo Path: $photo")
//            break
//        }
//    }
//
//    private fun getAllPhotos(): ArrayList<String> {
//        val projection = arrayOf(
//            MediaStore.Images.Media.DATA,
//            MediaStore.Images.Media.DISPLAY_NAME
//        )
//
//        val photos = ArrayList<String>()
//
//        // MediaStore에서 이미지를 가져오는 쿼리
//        val cursor: Cursor? = contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            null,
//            null,
//            null
//        )
//
//        cursor?.use {
//            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            while (it.moveToNext()) {
//                val photoPath = it.getString(columnIndexData)
//                photos.add(photoPath)
//            }
//        }
//
//        return photos
//    }
//}

class MyContentProvider : ContentProvider() {
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}

