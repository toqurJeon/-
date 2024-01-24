package com.example.moiroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        val kakao_logout_button = view.findViewById<Button>(R.id.kakao_logout_button) // 로그아웃 버튼
        val kakao_unlink_button = view.findViewById<Button>(R.id.kakao_unlink_button) // 회원탈퇴 버튼

        // 로그아웃 기능 구현
        kakao_logout_button.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(context, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    // 로그아웃 성공 시, 'isButtonClicked' 값을 초기화
                    val sharedPreferences = this.activity?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("isButtonClicked", false)
                    editor?.apply()

                    Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    activity?.finish()
                }
            }
        }

// 회원탈퇴 기능 구현
        kakao_unlink_button.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(context, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    // 회원탈퇴 성공 시, 'isButtonClicked' 값을 초기화
                    val sharedPreferences = this.activity?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("isButtonClicked", false)
                    editor?.apply()

                    Toast.makeText(context, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    activity?.finish()
                }
            }
        }

        // 사용자 정보 요청
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Toast.makeText(context, "사용자 정보 요청 실패: $error", Toast.LENGTH_SHORT).show()
            } else if (user != null) {
                val profile = user.kakaoAccount?.profile
                val email = user.kakaoAccount?.email
                val gender = user.kakaoAccount?.gender
                val ageRange = user.kakaoAccount?.ageRange
                val birthday = user.kakaoAccount?.birthday
                val birthyear = user.kakaoAccount?.birthyear
                val phoneNumber = user.kakaoAccount?.phoneNumber

                // 사용자 정보를 TextView에 출력
                view.findViewById<TextView>(R.id.nickname).text = "닉네임: ${profile?.nickname}"
                view.findViewById<TextView>(R.id.email).text = "이메일: $email"
                view.findViewById<TextView>(R.id.gender).text = "성별: ${gender?.name}"
                view.findViewById<TextView>(R.id.ageRange).text = "연령대: ${ageRange?.name}"
                view.findViewById<TextView>(R.id.birthday).text = "생일: $birthday"
                view.findViewById<TextView>(R.id.birthyear).text = "출생연도: $birthyear"
                view.findViewById<TextView>(R.id.phoneNumber).text = "전화번호: $phoneNumber"

                val profileImageUrl = profile?.profileImageUrl
                if (profileImageUrl != null) {
                    val profileImageView = view.findViewById<ImageView>(R.id.profileImage)
                    Glide.with(this).load(profileImageUrl).into(profileImageView)
                }
            }
        }

        return view
    }
}