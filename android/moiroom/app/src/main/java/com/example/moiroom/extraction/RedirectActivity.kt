package com.example.moiroom.extraction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moiroom.activity.NowMatchingActivity

class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("리디렉션","리디렉션")
        // Redirect URI로부터 데이터 추출
        val data: Uri? = intent?.data

        // 여기에서 데이터 처리
        if (data != null) {
            handleRedirectData(data)
        }

        // 액티비티를 시작한 후 즉시 종료

        val intent = Intent(this@RedirectActivity, NowMatchingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleRedirectData(uri: Uri) {
        // 여기에서 웹 페이지로부터 전달받은 데이터를 처리
        // 예: 인증 코드 추출 및 다음 단계 수행
    }
}