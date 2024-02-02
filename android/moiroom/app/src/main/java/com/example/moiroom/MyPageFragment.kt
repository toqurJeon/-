package com.example.moiroom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.Interest
import com.example.moiroom.data.Member
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.databinding.FragmentMyPageBinding
import com.example.moiroom.view.RadarChartView


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

        // 레이더 차트 만들기 ~~~~~
        val chartView = RadarChartView(context, null)

        chartView.setDataList(
            arrayListOf(
                RadarChartData(CharacteristicType.socialbility,memberData.socialbility.toFloat() / 100),
                RadarChartData(CharacteristicType.positivity,memberData.positivity.toFloat() / 100),
                RadarChartData(CharacteristicType.activity,memberData.activity.toFloat() / 100),
                RadarChartData(CharacteristicType.communion, memberData.communion.toFloat() / 100),
                RadarChartData(CharacteristicType.altruism, memberData.altruism.toFloat() / 100),
                RadarChartData(CharacteristicType.empathy, memberData.empathy.toFloat() / 100),
                RadarChartData(CharacteristicType.humor, memberData.humor.toFloat() / 100),
                RadarChartData(CharacteristicType.generous, memberData.generous.toFloat() / 100),
            )
        )
        binding.radarChartContainer.addView(chartView)

        // 사용자 정보 PATCH 페이지 이동
        binding.editButton.setOnClickListener {
            val intent = Intent(requireContext(), InfoupdateActivity::class.java)
            intent.putExtra("memberId", memberData.memberId)
            intent.putExtra("memberProfileImage", memberData.memberProfileImageUrl)
            intent.putExtra("memberNickname", memberData.memberNickname)
            intent.putExtra("memberIntroduction", memberData.memberIntroduction)
            intent.putExtra("metropolitanName", memberData.metropolitanName)
            intent.putExtra("cityName", memberData.cityName)
            intent.putExtra("memberRoomateSearchStatus", memberData.memberRoomateSearchStatus)

            startActivity(intent)
        }

        // 사용자 상세 정보 페이지 이동
        binding.detailButton.setOnClickListener {
            val intent = Intent(requireContext(), DetailchartActivity::class.java)
            intent.putExtra("memberData", memberData)

            startActivity(intent)
        }

        // 고급 설정 페이지 (로그아웃, 회원탈퇴)
        binding.advancedSettingButton.setOnClickListener {
            val intent = Intent(requireContext(), AdsettingActivity::class.java)
            startActivity(intent)
        }

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
            6520,
            7552,
            6993,
            7653,
            5683,
            4210,
            6020,
            8758,
            listOf(
                Interest(
                    "운동",
                    48
                ),
                Interest(
                    "음악",
                    36
                ),
                Interest(
                    "요리",
                    11
                ),
                Interest(
                    "게임",
                    5
                )
            )
        )
    }
}