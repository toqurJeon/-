package com.example.moiroom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moiroom.databinding.ActivityAdsettingBinding
import com.kakao.sdk.user.UserApiClient

class AdsettingActivity : AppCompatActivity() {

    // 레이아웃 바인딩
    private lateinit var binding: ActivityAdsettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdsettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logoutButton = binding.logoutButton // 로그아웃 버튼
        val unregisterButton = binding.unregisterButton // 회원탈퇴 버튼

        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }

        // 로그아웃 기능 구현
        logoutButton.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    // 로그아웃 성공 시, 'isButtonClicked' 값을 초기화
                    val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("isButtonClicked", false)
                    editor?.apply()

                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }

        // 회원탈퇴 기능 구현
        unregisterButton.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    // 회원탈퇴 성공 시, 'isButtonClicked' 값을 초기화
                    val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("isButtonClicked", false)
                    editor?.apply()

                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }

//        // 사용자 정보 요청
//        UserApiClient.instance.me { user, error ->
//            if (error != null) {
//                Toast.makeText(context, "사용자 정보 요청 실패: $error", Toast.LENGTH_SHORT).show()
//            } else if (user != null) {
//                val profile = user.kakaoAccount?.profile
//                val email = user.kakaoAccount?.email
//                val gender = user.kakaoAccount?.gender
//                val ageRange = user.kakaoAccount?.ageRange
//                val birthday = user.kakaoAccount?.birthday
//                val birthyear = user.kakaoAccount?.birthyear
//                val phoneNumber = user.kakaoAccount?.phoneNumber
//
//                // 사용자 정보를 TextView에 출력
//                binding.nickname.text = "닉네임: ${profile?.nickname}"
//                binding.email.text = "이메일: $email"
//                binding.gender.text = "성별: ${gender?.name}"
//                binding.ageRange.text = "연령대: ${ageRange?.name}"
//                binding.birthday.text = "생일: $birthday"
//                binding.birthyear.text = "출생연도: $birthyear"
//                binding.phoneNumber.text = "전화번호: $phoneNumber"
//
//                val profileImageUrl = profile?.profileImageUrl
//                if (profileImageUrl != null) {
//                    val profileImageView = binding.profileImage
//                    Glide.with(this).load(profileImageUrl).into(profileImageView)
//                }
//            }
//        }
    }

    override fun onBackPressed() {
        // 뒤로가기
        finish()
    }
}