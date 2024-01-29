package com.example.moiroom

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Half.toFloat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.moiroom.data.Interest
import com.example.moiroom.data.Member
import com.example.moiroom.databinding.FragmentMyPageBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class MyPageFragment : Fragment() {

    // 레이아웃 바인딩
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        val memberData: Member = getMemberData()

        val profileImageUrl = memberData.memberProfileImageUrl
        if (profileImageUrl != null) {
            val profileImageView = binding.memberProfileImage
            Glide.with(this).load(profileImageUrl).into(profileImageView)
        }

        binding.memberNickname.text = memberData.memberNickname
        binding.memberName.text = memberData.memberName
        binding.memberGender.text = memberData.memberGender
        binding.memberBirthYear.text = "${memberData.memberBirthYear}"
        binding.memberIntroduction.text = memberData.memberIntroduction

        val entries: ArrayList<RadarEntry> = ArrayList()
        entries.add(RadarEntry(memberData.socialbility.toFloat()/100))
        entries.add(RadarEntry(memberData.positivity.toFloat()/100))
        entries.add(RadarEntry(memberData.activity.toFloat()/100))
        entries.add(RadarEntry(memberData.communion.toFloat()/100))
        entries.add(RadarEntry(memberData.altruism.toFloat()/100))
        entries.add(RadarEntry(memberData.empathy.toFloat()/100))
        entries.add(RadarEntry(memberData.humor.toFloat()/100))
        entries.add(RadarEntry(memberData.generous.toFloat()/100))

        val labels = arrayOf("사교", "긍정", "활동", "공유", "이타", "공감", "감각", "관대")

        val radarDataSet = RadarDataSet(entries, "Data Label")

        radarDataSet.color = ContextCompat.getColor(requireContext(), R.color.darkorange)
        radarDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.grey)


        val radarData = RadarData()
        radarData.addDataSet(radarDataSet)

        val radarChart = binding.radarChart
        radarChart.data = radarData
        radarChart.description.isEnabled = false
        radarChart.webLineWidth = 1f
        radarChart.webLineWidthInner = 2f

        val xAxis: XAxis = radarChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        val yAxis: YAxis = radarChart.yAxis
        yAxis.axisMaximum = 90f
        yAxis.axisMinimum = 0f

        xAxis.textSize = 16f

        radarChart.invalidate()


//        val kakao_logout_button = binding.kakaoLogoutButton // 로그아웃 버튼
//        val kakao_unlink_button = binding.kakaoUnlinkButton // 회원탈퇴 버튼
//
//        // 로그아웃 기능 구현
//        kakao_logout_button.setOnClickListener {
//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    Toast.makeText(context, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
//                } else {
//                    // 로그아웃 성공 시, 'isButtonClicked' 값을 초기화
//                    val sharedPreferences = this.activity?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//                    val editor = sharedPreferences?.edit()
//                    editor?.putBoolean("isButtonClicked", false)
//                    editor?.apply()
//
//                    Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(context, MainActivity::class.java)
//                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                    activity?.finish()
//                }
//            }
//        }
//
//        // 회원탈퇴 기능 구현
//        kakao_unlink_button.setOnClickListener {
//            UserApiClient.instance.unlink { error ->
//                if (error != null) {
//                    Toast.makeText(context, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
//                } else {
//                    // 회원탈퇴 성공 시, 'isButtonClicked' 값을 초기화
//                    val sharedPreferences = this.activity?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//                    val editor = sharedPreferences?.edit()
//                    editor?.putBoolean("isButtonClicked", false)
//                    editor?.apply()
//
//                    Toast.makeText(context, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(context, MainActivity::class.java)
//                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                    activity?.finish()
//                }
//            }
//        }
//
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
        return binding.root
    }
    private fun getMemberData(): Member {
        return Member(
            1,
            "https://images.dog.ceo/breeds/samoyed/n02111889_6249.jpg",
            "안드레이",
            "남자",
            "김민식",
            1999,
            "서울특별시",
            "강남구",
            "멍멍이를 엄청 좋아해요. 댕댕.",
            1,
            5183,
            2389,
            6893,
            8932,
            6783,
            5938,
            5890,
            8758,
            listOf(
                Interest(
                    "운동",
                    57
                ),
                Interest(
                    "음악",
                    33
                ),
                Interest(
                    "요리",
                    10
                )
            )
        )
    }
}