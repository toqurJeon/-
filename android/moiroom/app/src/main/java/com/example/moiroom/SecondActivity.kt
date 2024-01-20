package com.example.moiroom

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moiroom.MainActivity
import com.example.moiroom.R
import com.kakao.sdk.user.UserApiClient

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val kakao_logout_button = findViewById<Button>(R.id.kakao_logout_button) // 로그인 버튼

        kakao_logout_button.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        val kakao_unlink_button = findViewById<Button>(R.id.kakao_unlink_button) // 로그인 버튼

        kakao_unlink_button.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Toast.makeText(this, "사용자 정보 요청 실패: $error", Toast.LENGTH_SHORT).show()
            } else if (user != null) {
                val profile = user.kakaoAccount?.profile
                val email = user.kakaoAccount?.email
                val gender = user.kakaoAccount?.gender
                val ageRange = user.kakaoAccount?.ageRange
                val birthday = user.kakaoAccount?.birthday
                val birthyear = user.kakaoAccount?.birthyear
                val phoneNumber = user.kakaoAccount?.phoneNumber

                findViewById<TextView>(R.id.nickname).text = "닉네임: ${profile?.nickname}"
                findViewById<TextView>(R.id.email).text = "이메일: $email"
                findViewById<TextView>(R.id.gender).text = "성별: $gender"
                findViewById<TextView>(R.id.ageRange).text = "연령대: $ageRange"
                findViewById<TextView>(R.id.birthday).text = "생일: $birthday"
                findViewById<TextView>(R.id.birthyear).text = "출생연도: $birthyear"
                findViewById<TextView>(R.id.phoneNumber).text = "전화번호: $phoneNumber"

                val profileImageUrl = profile?.profileImageUrl
                if (profileImageUrl != null) {
                    val profileImageView = findViewById<ImageView>(R.id.profileImage)
                    Glide.with(this).load(profileImageUrl).into(profileImageView)
                }
            }
        }

    }

}