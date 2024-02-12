package com.example.moiroom

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.Interest
import com.example.moiroom.data.Member
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.data.UserResponse
import com.example.moiroom.databinding.FragmentMyPageBinding
import com.example.moiroom.utils.cacheUserInfo
import com.example.moiroom.utils.getUserInfo
import com.example.moiroom.view.RadarChartView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyPageFragment : Fragment() {

    // 레이아웃 바인딩
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        val cachedUserInfo: UserResponse.Data.Member? = cacheUserInfo.get("userInfo")
        if (cachedUserInfo != null) {
            val memberData: UserResponse.Data.Member = cachedUserInfo
            setUI(memberData)
        }
        return binding.root
    }

    private fun setUI(memberData: UserResponse.Data.Member) {
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
                RadarChartData(CharacteristicType.sociability,memberData.characteristic.sociability.toFloat() / 100),
                RadarChartData(CharacteristicType.positivity,memberData.characteristic.positivity.toFloat() / 100),
                RadarChartData(CharacteristicType.activity,memberData.characteristic.activity.toFloat() / 100),
                RadarChartData(CharacteristicType.communion, memberData.characteristic.communion.toFloat() / 100),
                RadarChartData(CharacteristicType.altruism, memberData.characteristic.altruism.toFloat() / 100),
                RadarChartData(CharacteristicType.empathy, memberData.characteristic.empathy.toFloat() / 100),
                RadarChartData(CharacteristicType.humor, memberData.characteristic.humor.toFloat() / 100),
                RadarChartData(CharacteristicType.generous, memberData.characteristic.generous.toFloat() / 100),
            ),
            null
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
            intent.putExtra("memberRoommateSearchStatus", memberData.memberRoommateSearchStatus)  // 수정된 코드

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
    }
}
