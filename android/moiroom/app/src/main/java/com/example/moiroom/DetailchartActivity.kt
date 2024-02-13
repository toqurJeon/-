package com.example.moiroom

import android.animation.ValueAnimator
import android.app.Dialog
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
import com.example.moiroom.data.UserResponse
import com.example.moiroom.databinding.ActivityDetailchartBinding
import com.example.moiroom.databinding.DialogCharacterInformationBinding
import com.example.moiroom.utils.getBGColorCharacter
import com.example.moiroom.utils.getCharacterDescription
import com.example.moiroom.utils.getCharacterDetailDescription
import com.example.moiroom.utils.getCharacterIcon
import com.example.moiroom.utils.getColorCharacter
import com.example.moiroom.view.RadarChartView
import java.text.DecimalFormat

class DetailchartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailchartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailchartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 멤버 데이터 MyPageFragment로부터 받아오기
        val memberData: UserResponse.Data.Member? = intent.getParcelableExtra<UserResponse.Data.Member>("memberData")

        Log.d("TAG", "onCreate: $memberData")

        if (memberData != null) {
            // 성향 레이더 차트
            val chartView = RadarChartView(this, null)

            val chartData = arrayListOf(
                RadarChartData(CharacteristicType.sociability, memberData.characteristic.sociability.toFloat() / 100),
                RadarChartData(CharacteristicType.positivity, memberData.characteristic.positivity.toFloat() / 100),
                RadarChartData(CharacteristicType.activity, memberData.characteristic.activity.toFloat() / 100),
                RadarChartData(CharacteristicType.communion, memberData.characteristic.communion.toFloat() / 100),
                RadarChartData(CharacteristicType.altruism, memberData.characteristic.altruism.toFloat() / 100),
                RadarChartData(CharacteristicType.empathy, memberData.characteristic.empathy.toFloat() / 100),
                RadarChartData(CharacteristicType.humor, memberData.characteristic.humor.toFloat() / 100),
                RadarChartData(CharacteristicType.generous, memberData.characteristic.generous.toFloat() / 100)
            )
            chartView.setDataList(chartData, null)
            binding.radarChartContainer.addView(chartView)

            // 성향 목록
            binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
            val characterAdapter = CharacterAdapter(this, chartData, null) { clickedData, position ->
                updateUI(clickedData[0])
                performAnimation(clickedData[0])
            }
            binding.recyclerView.adapter = characterAdapter

            // 관심사 차트
            val squareChart = binding.squareChartView
            squareChart.setData(memberData.interests)

            // 관심사 목록
            binding.interestRecyclerView.layoutManager = LinearLayoutManager(this)
            val interestAdapter = InterestAdapter(this, memberData.interests)
            binding.interestRecyclerView.adapter = interestAdapter

            // 수면 차트
//            val sleepChart = binding.sleepChartView
//            sleepChart.setSleepTime(memberData.characteristic.sleepAt, memberData.characteristic.wakeUpAt)
//            binding.sleepTimeText.text = memberData.characteristic.sleepAt
//            binding.wakeTimeText.text = memberData.characteristic.wakeUpAt

            binding.characterDescriptionButton.setOnClickListener {
                Log.d("MYTAG", "onCreate: ${binding.characterDetailName.text}")

                val dialog = Dialog(this, R.style.DialogTheme)
                val dialogBinding = DialogCharacterInformationBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)

                dialogBinding.characterTitle.text = "${binding.characterDetailName.text}"
                val detailDescription = getCharacterDetailDescription(binding.characterDetailName.text.toString())
                dialogBinding.characterDescription.text = detailDescription

                dialogBinding.confirmButton.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
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
