package com.example.moiroom

import CharacterAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
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

            val adapter = CharacterAdapter(chartData)
            binding.recyclerView.adapter = adapter
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