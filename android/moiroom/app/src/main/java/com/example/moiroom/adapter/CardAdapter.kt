package com.example.moiroom.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moiroom.ChatActivity
import com.example.moiroom.R
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.MatchedMember
import com.example.moiroom.data.Member
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.databinding.MatchedListLayoutBinding
import com.example.moiroom.databinding.MatchedLayoutBinding
import com.example.moiroom.utils.getBGColorCharacter
import com.example.moiroom.utils.getCharacterDescription
import com.example.moiroom.utils.getCharacterIcon
import com.example.moiroom.utils.getColorCharacter
import com.example.moiroom.view.RadarChartView
import com.google.android.material.appbar.AppBarLayout
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.roundToInt

class CardAdapter(
    private val context: Context,
    private val cardInfoList: List<MatchedMember>,
    private val myInfo: Member,
    private val isToggleButtonChecked: Boolean,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var itemClickListener: ((position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: ((position: Int) -> Unit)?) {
        this.itemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (isToggleButtonChecked) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val binding = MatchedLayoutBinding.inflate(inflater, parent, false)
            CardViewHolder1(binding)
        } else {
            val binding = MatchedListLayoutBinding.inflate(inflater, parent, false)
            CardViewHolder2(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardInfo = cardInfoList[position]
        if (holder is CardViewHolder1) {
            holder.bind(cardInfo)
        } else if (holder is CardViewHolder2) {
            holder.bind(cardInfo)

            if (position == cardInfoList.size - 1) {
                val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
                val marginBottomInPx = convertDpToPixel(96)
                layoutParams.bottomMargin = marginBottomInPx
                holder.itemView.layoutParams = layoutParams
            }
        }
    }

    inner class CardViewHolder1(private val binding: MatchedLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val chartView = RadarChartView(context, null)

        fun bind(cardInfo: MatchedMember) {
            binding.apply {

                // 상대방 정보 데이터 바인딩
                matchRate.text = "${cardInfo.matchRate}"
                matchIntroduction.text = cardInfo.matchIntroduction
                nickname.text = cardInfo.memberNickname
                location.text = "${cardInfo.metropolitanName} ${cardInfo.cityName}"
                introduction.text = cardInfo.memberIntroduction

                // 상대방 프로필 이미지 불러오기
                Glide.with(binding.root.context).load(cardInfo.memberProfileImageUrl).into(binding.profileImage)

                // 매칭률에 따라서 색상 설정
                val matchRateResult = cardInfo.matchRate
                if (matchRateResult >= 90) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.main_orange))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.main_orange))
                } else if (matchRateResult >= 80) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_over80))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_over80))
                } else if (matchRateResult >= 70) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_over70))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_over70))
                } else if (matchRateResult >= 60) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_over60))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_over60))
                } else {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_else))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_else))
                }

                // 상대방 성향 데이터 삽입
                val dataList = arrayListOf(
                    RadarChartData(CharacteristicType.socialbility, cardInfo.socialbility.toFloat() / 100),
                    RadarChartData(CharacteristicType.positivity, cardInfo.positivity.toFloat() / 100),
                    RadarChartData(CharacteristicType.activity, cardInfo.activity.toFloat() / 100),
                    RadarChartData(CharacteristicType.communion, cardInfo.communion.toFloat() / 100),
                    RadarChartData(CharacteristicType.altruism, cardInfo.altruism.toFloat() / 100),
                    RadarChartData(CharacteristicType.empathy, cardInfo.empathy.toFloat() / 100),
                    RadarChartData(CharacteristicType.humor, cardInfo.humor.toFloat() / 100),
                    RadarChartData(CharacteristicType.generous, cardInfo.generous.toFloat() / 100)
                )
                // 본인 성향 데이터 삽입
                val myDataList = arrayListOf(
                    RadarChartData(CharacteristicType.socialbility, myInfo.socialbility.toFloat() / 100),
                    RadarChartData(CharacteristicType.positivity, myInfo.positivity.toFloat() / 100),
                    RadarChartData(CharacteristicType.activity, myInfo.activity.toFloat() / 100),
                    RadarChartData(CharacteristicType.communion, myInfo.communion.toFloat() / 100),
                    RadarChartData(CharacteristicType.altruism, myInfo.altruism.toFloat() / 100),
                    RadarChartData(CharacteristicType.empathy, myInfo.empathy.toFloat() / 100),
                    RadarChartData(CharacteristicType.humor, myInfo.humor.toFloat() / 100),
                    RadarChartData(CharacteristicType.generous, myInfo.generous.toFloat() / 100)
                )
                chartView.setDataList(myDataList, dataList)

                // 성향 레이더 차트 생성
                radarChartContainer.removeAllViews()
                radarChartContainer.addView(chartView)

                // 성향 비교 카드
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                val characterAdapter = CharacterAdapter(context, dataList, myDataList) { clickedData, position ->
                    characterIcon.setImageResource(getCharacterIcon(clickedData[0].type))
                    characterIcon.setColorFilter(getColorCharacter(clickedData[0].type.value, context))
                    characterDetailName.text = clickedData[0].type.value
                    characterDetailDescription.text = getCharacterDescription(clickedData[0].type)
                    characterLocation.setColorFilter(getColorCharacter(clickedData[0].type.value, context))
                    pinBase.setCardBackgroundColor(getBGColorCharacter(clickedData[0].type.value, context))

                    val decimalFormat = DecimalFormat("#.##")
                    val abs = abs(clickedData[0].value - clickedData[1].value)
                    myCharacterDescription.text = "나와 ${cardInfo.memberNickname}님은 ${clickedData[0].type.value} 성향이 ${decimalFormat.format(abs)}% 차이가 나요"
                    performAnimation(clickedData[0], clickedData[1], binding)
                }
                recyclerView.adapter = characterAdapter

                val squareChart = binding.squareChartView
                squareChart.setData(cardInfo.interest)

                // 관심사 목록
                binding.interestRecyclerView.layoutManager = LinearLayoutManager(context)
                val interestAdapter = InterestAdapter(context, cardInfo.interest)
                binding.interestRecyclerView.adapter = interestAdapter

                // 수면 차트
                val sleepChart = binding.sleepChartView
                sleepChart.setSleepTime(cardInfo.sleepAt, cardInfo.wakeUpAt)
                binding.sleepTimeText.text = cardInfo.sleepAt
                binding.wakeTimeText.text = cardInfo.wakeUpAt

                // 밑줄 뷰의 너비 조정
                matchIntroduction.post {
                    val layoutParams = underline.layoutParams
                    Log.d("in CardAdapter", "bind: ${layoutParams.width}, ${matchIntroduction.width}")
                    layoutParams.width = (matchIntroduction.width * 1.1).roundToInt()
                    underline.layoutParams = layoutParams
                    underline.visibility = View.VISIBLE
                }

                appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    val collapsedRange = appBarLayout.totalScrollRange
                    val currentOffset = abs(verticalOffset)

                    val alpha = (currentOffset.toFloat() / collapsedRange.toFloat() * 255).toInt()
                    val backgroundColor = Color.argb(200, 255, 255, 255)

                    // 변경할 배경색을 설정합니다.
                    appBar.setBackgroundColor(backgroundColor)
                })

                chatbuttonContainer.setOnClickListener {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("memberId", cardInfo.memberId)
                    Log.d("TAG!!!!!!!!!!!!!!!!!!!!!!", "bind: ${cardInfo.memberId}")
                    context.startActivity(intent)
                }
            }
        }
    }

    inner class CardViewHolder2(private val binding: MatchedListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 클릭 이벤트 처리
            binding.parentLayout.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(position)
                }
            }
        }
        fun bind(cardInfo: MatchedMember) {
            binding.apply {

                // 상대방 정보 바인딩
                matchRate.text = "${cardInfo.matchRate}"
                nickname.text = cardInfo.memberNickname
                location.text = "${cardInfo.metropolitanName}, ${cardInfo.cityName}"
                // 상대방 프로필 이미지 불러오기
                Glide.with(binding.root.context).load(cardInfo.memberProfileImageUrl).into(binding.profileImage)

                // 매칭률에 따라서 색상 변경
                val matchRateResult = cardInfo.matchRate
                if (matchRateResult >= 90) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.main_orange))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.main_orange))
                } else if (matchRateResult >= 80) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_over80))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_over80))
                } else if (matchRateResult >= 70) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_over70))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_over70))
                } else if (matchRateResult >= 60) {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_over60))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_over60))
                } else {
                    matchRate.setTextColor(ContextCompat.getColor(context, R.color.rate_else))
                    matchRateSymbol.setTextColor(ContextCompat.getColor(context, R.color.rate_else))
                }

                // 채팅방 생성 및 이동
                chatbuttonContainer.setOnClickListener {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("memberId", cardInfo.memberId)
                    Log.d("MY TAG", "bind: ${cardInfo.memberId}")
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount() = cardInfoList.size

    fun performAnimation(clickedData: RadarChartData, clickedData2: RadarChartData, binding: MatchedLayoutBinding) {
        val newValue = clickedData.value.coerceIn(0f, 100f)

        // 레이아웃이 로딩되지 않았을 때, 애니메이션 재 시작
        if (binding == null || binding.pinWrapper.width == 0) {
            binding?.characterLocation?.post {
                performAnimation(clickedData, clickedData2, binding)
            }
        }

        val currentMargin = (binding.characterLocation.layoutParams as ViewGroup.MarginLayoutParams).leftMargin
        val newMargin = (newValue / 100 * binding.pinWrapper.width).toInt()

        Log.d("MYTAG", "performAnimation: $newValue, $currentMargin, $newMargin")

        ValueAnimator.ofInt(currentMargin, newMargin).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val params = binding.characterLocation.layoutParams as ViewGroup.MarginLayoutParams
                params.leftMargin = animator.animatedValue as Int
                binding.characterLocation.layoutParams = params
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    // 애니메이션 종료
                    performAnimation2(clickedData2, binding)
                }
            })
            start()
        }
    }

    fun performAnimation2(clickedData: RadarChartData, binding: MatchedLayoutBinding) {
        val newValue = clickedData.value.coerceIn(0f, 100f)

        val currentMargin = (binding.characterLocation2.layoutParams as ViewGroup.MarginLayoutParams).leftMargin
        val newMargin = (newValue / 100 * binding.pinWrapper.width).toInt()

        Log.d("MYTAG", "performAnimation: $newValue, $currentMargin, $newMargin")

        ValueAnimator.ofInt(currentMargin, newMargin).apply {
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val params = binding.characterLocation2.layoutParams as ViewGroup.MarginLayoutParams
                params.leftMargin = animator.animatedValue as Int
                binding.characterLocation2.layoutParams = params
            }
            start()
        }
    }

    private fun convertDpToPixel(dp: Int): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density).toInt()
    }

//    abstract class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val matchingRate: TextView = view.findViewById(R.id.matchingRate)
//        val name: TextView = view.findViewById(R.id.name)
//        val location: TextView = view.findViewById(R.id.location)
//    }
//
//    inner class CardViewHolder1(view: View) : CardViewHolder(view) {
//        val summary: TextView = view.findViewById(R.id.summary)
//        val introduction: TextView = view.findViewById(R.id.introduction)
//        val profileImage: ImageView = view.findViewById(R.id.profileImage)
//        val underline: View = view.findViewById(R.id.underline)
//        val detailButton: Button = view.findViewById(R.id.detailButton)
//    }
//
//    class CardViewHolder2(view: View) : CardViewHolder(view) {
//        val introduction: TextView = view.findViewById(R.id.introduction)
//        val profileImage: ImageView = view.findViewById(R.id.profileImage)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val cardInfo = cardInfoList[position]
//        if (holder is CardViewHolder1) {
//            holder.matchingRate.text = "${cardInfo.matchRate}%"
//            holder.summary.text = cardInfo.matchIntroduction
////            holder.profileImage.setImageResource(cardInfo.memberProfileImageUrl)
//            holder.name.text = cardInfo.memberNickname
//            holder.location.text = cardInfo.metropolitanName + cardInfo.cityName
//            holder.introduction.text = cardInfo.memberIntroduction
//
//            // 밑줄 뷰의 너비를 요약 텍스트뷰의 너비와 같게 설정
//            holder.summary.post {
//                val layoutParams = holder.underline.layoutParams
//                layoutParams.width = holder.summary.width
//                holder.underline.layoutParams = layoutParams
//            }
//
//            // 기존 리스너 제거
//            holder.summary.viewTreeObserver.removeOnGlobalLayoutListener(holder.summary.tag as? ViewTreeObserver.OnGlobalLayoutListener)
//
//            val summaryObserver = holder.summary.viewTreeObserver
//            val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    val layoutParams = holder.underline.layoutParams
//                    layoutParams.width = holder.summary.width
//                    holder.underline.layoutParams = layoutParams
//
//                    // 무한 루프를 방지하기 위해 콜백 제거
//                    holder.summary.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                }
//            }
//
//            // 리스너를 태그에 저장
//            holder.summary.tag = globalLayoutListener
//            summaryObserver.addOnGlobalLayoutListener(globalLayoutListener)
//
//            holder.detailButton.setOnClickListener {
//                cardItemClickListener.onCardDetailClick(cardInfo) // 버튼 클릭 이벤트 처리
//            }
//
//            holder.detailButton.setOnClickListener {
//                val fragmentManager = (holder.detailButton.context as AppCompatActivity).supportFragmentManager
//
//                // NewCardDetailDialogFragment 인스턴스 생성
//                val newCardDetailDialogFragment = NewCardDetailDialogFragment.newInstance(cardInfo)
//
//                // DialogFragment를 보여주는 일반적인 방법을 사용
//                newCardDetailDialogFragment.show(fragmentManager, "cardDetail")
//            }
//
//        } else if (holder is CardViewHolder2) {
//            holder.matchingRate.text = "${cardInfo.matchRate}%"
//            holder.introduction.text = cardInfo.matchIntroduction
//            holder.profileImage.setImageResource(cardInfo.profileImage)
//            holder.name.text = cardInfo.memberNickname
//            holder.location.text = cardInfo.metropolitanName + cardInfo.cityName
//        }
//    }
//    override fun getItemCount() = cardInfoList.size
}



