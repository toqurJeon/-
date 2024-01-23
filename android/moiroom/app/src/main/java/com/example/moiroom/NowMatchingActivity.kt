package com.example.moiroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NowMatchingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_matching) // 레이아웃 파일명은 실제 레이아웃 파일명으로 변경해야 합니다.

        val startMatchingButton: TextView = findViewById(R.id.startMatchingButton)
        val relativeLayout: RelativeLayout = findViewById(R.id.relativeLayout) // RelativeLayout 참조 추가

        val onClickListener = View.OnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)

            // 클릭 여부를 SharedPreferences에 저장
            val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isButtonClicked", false)
            editor.apply()
        }

        // '매칭 시작하기' 버튼과 RelativeLayout에 동일한 클릭 리스너 설정
        startMatchingButton.setOnClickListener(onClickListener)
        relativeLayout.setOnClickListener(onClickListener)
    }
}
