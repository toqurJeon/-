package com.example.moiroom

import CharacterAdapter
import InterestAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.Member
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.databinding.ActivityDetailchartBinding

import com.example.moiroom.view.RadarChartView


class DetailchartActivity : AppCompatActivity() {

    // 레이아웃 바인딩
    private lateinit var binding: ActivityDetailchartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailchartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent로 보낸 데이터 받기
        val memberData: Member? = intent.getParcelableExtra<Member>("memberData")

        // memberData nullable 상태 감지
        if (memberData != null) {
            val chartView = RadarChartView(this, null)
            val chartData = arrayListOf(
                RadarChartData(
                    CharacteristicType.socialbility,
                    memberData.socialbility.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.positivity,
                    memberData.positivity.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.activity,
                    memberData.activity.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.communion,
                    memberData.communion.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.altruism,
                    memberData.altruism.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.empathy,
                    memberData.empathy.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.humor,
                    memberData.humor.toFloat() / 100
                ),
                RadarChartData(
                    CharacteristicType.generous,
                    memberData.generous.toFloat() / 100
                )
            )

            chartView.setDataList(chartData)
            binding.radarChartContainer.addView(chartView)

            // 성향 데이터 카드 그리드
            binding.recyclerView.layoutManager = GridLayoutManager(this, 4)

            val characterAdapter = CharacterAdapter(chartData)
            binding.recyclerView.adapter = characterAdapter

            // 관심사 사각형 파이 그래프
            val squareChart = binding.squareChartView
            squareChart.setData(memberData.interest)

            // 관심사 데이터 카드 그리드
            binding.interestRecyclerView.layoutManager = LinearLayoutManager(this)

            val interestAdapter = InterestAdapter(this, memberData.interest)
            binding.interestRecyclerView.adapter = interestAdapter

        }

        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        // 뒤로가기
        finish()
    }
}