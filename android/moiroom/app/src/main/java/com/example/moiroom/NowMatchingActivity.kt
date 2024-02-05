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

        // SharedPreferences에서 'isButtonClicked' 값을 가져옴
        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)

        if (isButtonClicked) {
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_now_matching)

//            val startMatchingButton: TextView = findViewById(R.id.startMatchingButton)
            val relativeLayout: RelativeLayout = findViewById(R.id.relativeLayout)

            val onClickListener = View.OnClickListener {
                val intent = Intent(this, LoadingActivity::class.java)
                startActivity(intent)

                // 클릭 여부를 SharedPreferences에 저장
                val editor = sharedPreferences.edit()
                editor.putBoolean("isButtonClicked", true) // 버튼 클릭 후 'true'로 변경
                editor.apply()
            }

//            startMatchingButton.setOnClickListener(onClickListener)
            relativeLayout.setOnClickListener(onClickListener)
        }
    }
}
