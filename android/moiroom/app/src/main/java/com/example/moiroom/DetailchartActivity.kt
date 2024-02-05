package com.example.moiroom

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moiroom.adapter.CharacterAdapter
import com.example.moiroom.adapter.InterestAdapter
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.Member
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.databinding.ActivityDetailchartBinding
import com.example.moiroom.utils.getBGColorCharacter
import com.example.moiroom.utils.getCharacterDescription
import com.example.moiroom.utils.getCharacterIcon
import com.example.moiroom.utils.getColorCharacter
import com.example.moiroom.view.RadarChartView
import java.text.DecimalFormat

class DetailchartActivity : AppCompatActivity() {

    private lateinit var binding: com.example.moiroom.databinding.ActivityDetailchartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailchartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 멤버 데이터 MyPageFragment로부터 받아오기
        val memberData: Member? = intent.getParcelableExtra<Member>("memberData")

        if (memberData != null) {
            // 성향 레이더 차트
            val chartView = RadarChartView(this, null)
            val chartData = arrayListOf(
                RadarChartData(CharacteristicType.socialbility, memberData.socialbility.toFloat() / 100),
                RadarChartData(CharacteristicType.positivity, memberData.positivity.toFloat() / 100),
                RadarChartData(CharacteristicType.activity, memberData.activity.toFloat() / 100),
                RadarChartData(CharacteristicType.communion, memberData.communion.toFloat() / 100),
                RadarChartData(CharacteristicType.altruism, memberData.altruism.toFloat() / 100),
                RadarChartData(CharacteristicType.empathy, memberData.empathy.toFloat() / 100),
                RadarChartData(CharacteristicType.humor, memberData.humor.toFloat() / 100),
                RadarChartData(CharacteristicType.generous, memberData.generous.toFloat() / 100)
            )
            chartView.setDataList(chartData)
            binding.radarChartContainer.addView(chartView)

            // 성향 목록
            binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
            val characterAdapter = CharacterAdapter(this, chartData) { clickedData, position ->
                updateUI(clickedData)
                performAnimation(clickedData)
            }
            binding.recyclerView.adapter = characterAdapter

            // 관심사 차트
            val squareChart = binding.squareChartView
            squareChart.setData(memberData.interest)

            // 관심사 목록
            binding.interestRecyclerView.layoutManager = LinearLayoutManager(this)
            val interestAdapter = InterestAdapter(this, memberData.interest)
            binding.interestRecyclerView.adapter = interestAdapter

            // 수면 차트
            val sleepChart = binding.sleepChartView
            sleepChart.setSleepTime(memberData.sleepAt, memberData.wakeUpAt)
            binding.sleepTimeText.text = memberData.sleepAt
            binding.wakeTimeText.text = memberData.wakeUpAt
        }

        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun updateUI(clickedData: RadarChartData) {
        binding.characterIcon.setImageResource(getCharacterIcon(clickedData.type))
        binding.characterIcon.setColorFilter(getColorCharacter(clickedData.type.value, this))
        binding.characterDetailName.text = clickedData.type.value
        binding.characterDetailDescription.text = getCharacterDescription(clickedData.type)
        binding.characterLocation.setColorFilter(getColorCharacter(clickedData.type.value, this))
        binding.pinBase.setCardBackgroundColor(getBGColorCharacter(clickedData.type.value, this))

        val decimalFormat = DecimalFormat("#.##")
        binding.myCharacterDescription.text = "상위 ${decimalFormat.format(100 - clickedData.value)}%의 ${clickedData.type.value} 성향을 가지고 있어요"
    }

    fun performAnimation(clickedData: RadarChartData) {
        val newValue = clickedData.value.coerceIn(0f, 100f)

        // 레이아웃이 로딩되지 않았을 때, 애니메이션 재 시작
        if (binding == null || binding.pinWrapper.width == 0) {
            binding?.characterLocation?.post {
                performAnimation(clickedData)
            }
        }

        val currentMargin = (binding.characterLocation.layoutParams as ViewGroup.MarginLayoutParams).leftMargin
        val newMargin = (newValue / 100 * binding.pinWrapper.width).toInt()

        Log.d("MYTAG", "performAnimation: $newValue, $currentMargin, $newMargin")

        ValueAnimator.ofInt(currentMargin, newMargin).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val params = binding.characterLocation.layoutParams as ViewGroup.MarginLayoutParams
                params.leftMargin = animator.animatedValue as Int
                binding.characterLocation.layoutParams = params
            }
            start()
        }
    }
}
