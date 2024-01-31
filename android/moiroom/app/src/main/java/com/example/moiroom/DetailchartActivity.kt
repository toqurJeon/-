package com.example.moiroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moiroom.data.Member
import com.example.moiroom.databinding.ActivityDetailchartBinding


class DetailchartActivity : AppCompatActivity() {

    // 레이아웃 바인딩
    private lateinit var binding: ActivityDetailchartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailchartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memberData = intent.getParcelableExtra<Member>("memberData")

        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        // 뒤로가기
        finish()
    }
}