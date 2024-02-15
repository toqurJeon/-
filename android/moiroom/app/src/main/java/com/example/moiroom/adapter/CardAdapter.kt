package com.example.moiroom.adapter

import ApiService
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moiroom.ChatActivity
import com.example.moiroom.NowMatchingAfterFragment
import com.example.moiroom.R
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.CombinedInterest
import com.example.moiroom.data.Interest
import com.example.moiroom.data.MatchedMember
import com.example.moiroom.data.MatchedMemberData
import com.example.moiroom.data.Member
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.data.UserResponse
import com.example.moiroom.databinding.DialogFindMetropolitanBinding
import com.example.moiroom.databinding.MatchedListLayoutBinding
import com.example.moiroom.databinding.MatchedLayoutBinding
import com.example.moiroom.utils.getBGColorCharacter
import com.example.moiroom.utils.getCharacterDescription
import com.example.moiroom.utils.getCharacterIcon
import com.example.moiroom.utils.getColorCharacter
import com.example.moiroom.utils.getInterestName
import com.example.moiroom.view.RadarChartView
import com.example.moiroom.view.RectangleChartView
import com.github.mikephil.charting.utils.Utils.convertDpToPixel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.roundToInt

class CardAdapter(
    private val context: Context,
    private val cardInfoList: List<MatchedMemberData>,
    private val myInfo: UserResponse.Data.Member,
    private val isToggleButtonChecked: Boolean,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnCharcterClickListener {
        fun onCharacterDescriptionClicked(description: String)
    }

    var characterClickListener: OnCharcterClickListener? = null

    fun setOnCharacterClickListener(listener: OnCharcterClickListener?) {
        this.characterClickListener = listener
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
                val marginBottomInPx = convertDpToPixel(184)
                layoutParams.bottomMargin = marginBottomInPx
                holder.itemView.layoutParams = layoutParams
            } else {
                val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
                val marginBottomInPx = convertDpToPixel(0)
                layoutParams.bottomMargin = marginBottomInPx
                holder.itemView.layoutParams = layoutParams
            }
        }
    }

    inner class CardViewHolder1(private val binding: MatchedLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val chartView = RadarChartView(context, null)

        fun bind(cardInfo: MatchedMemberData) {
            binding.apply {

                // 상대방 정보 데이터 바인딩
                val matchRateResult = cardInfo.matchRate.toFloat() / 100
                val decimalFormat2 = DecimalFormat("#.#")
                matchRate.text = "${decimalFormat2.format(matchRateResult)}"
                matchIntroduction.text = cardInfo.matchIntroduction
                nickname.text = cardInfo.member.memberNickname
                location.text = "${cardInfo.member.metropolitanName} ${cardInfo.member.cityName}"
                introduction.text = cardInfo.member.memberIntroduction

                // 상대방 프로필 이미지 불러오기
                Glide.with(binding.root.context)
                    .load(cardInfo.member.memberProfileImageUrl)
                    .placeholder(R.drawable.sample_profile1)
                    .into(binding.profileImage)

                // 매칭률에 따라서 색상 설정
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
                    RadarChartData(CharacteristicType.sociability, cardInfo.member.characteristic.sociability.toFloat() / 100),
                    RadarChartData(CharacteristicType.positivity, cardInfo.member.characteristic.positivity.toFloat() / 100),
                    RadarChartData(CharacteristicType.activity, cardInfo.member.characteristic.activity.toFloat() / 100),
                    RadarChartData(CharacteristicType.communion, cardInfo.member.characteristic.communion.toFloat() / 100),
                    RadarChartData(CharacteristicType.altruism, cardInfo.member.characteristic.altruism.toFloat() / 100),
                    RadarChartData(CharacteristicType.empathy, cardInfo.member.characteristic.empathy.toFloat() / 100),
                    RadarChartData(CharacteristicType.humor, cardInfo.member.characteristic.humor.toFloat() / 100),
                    RadarChartData(CharacteristicType.generous, cardInfo.member.characteristic.generous.toFloat() / 100)
                )
                // 본인 성향 데이터 삽입
                val myDataList = arrayListOf(
                    RadarChartData(CharacteristicType.sociability, myInfo.characteristic.sociability.toFloat() / 100),
                    RadarChartData(CharacteristicType.positivity, myInfo.characteristic.positivity.toFloat() / 100),
                    RadarChartData(CharacteristicType.activity, myInfo.characteristic.activity.toFloat() / 100),
                    RadarChartData(CharacteristicType.communion, myInfo.characteristic.communion.toFloat() / 100),
                    RadarChartData(CharacteristicType.altruism, myInfo.characteristic.altruism.toFloat() / 100),
                    RadarChartData(CharacteristicType.empathy, myInfo.characteristic.empathy.toFloat() / 100),
                    RadarChartData(CharacteristicType.humor, myInfo.characteristic.humor.toFloat() / 100),
                    RadarChartData(CharacteristicType.generous, myInfo.characteristic.generous.toFloat() / 100)
                )
                chartView.setDataList(myDataList, dataList)

                // 성향 레이더 차트 생성
                radarChartContainer.removeAllViews()
                radarChartContainer.addView(chartView)
                radarChartRoommateLegendColor.setCardBackgroundColor(context.getColor(R.color.rate_over80))
                radarChartRoommateLegend.text = cardInfo.member.memberNickname

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
                    myCharacterDescription.text = "${cardInfo.member.memberNickname}님과 ${clickedData[0].type.value} 성향이 ${decimalFormat.format(abs)}% 차이 나요."
                    performAnimation(clickedData[0], clickedData[1], binding)
                }
                recyclerView.adapter = characterAdapter

                // 성향 상세정보 다이얼로그 띄우기 위해서 Fragment에 데이터 전달
//                characterDescriptionButton.setOnClickListener {
//                    val description = characterDetailName.text.toString()
//                    characterClickListener?.onCharacterDescriptionClicked(description)
//                }

//                // 상대방 관심사 사각형 차트
//                val squareChart = binding.squareChartView
//                squareChart.setData(cardInfo.member.interests)
//
//                // 내 관심사 사각형 차트
//                val squareChart2 = binding.squareChartView2
//                squareChart2.setData(myInfo.interests)

                // 상대방 관심사 목록
//                binding.interestRecyclerView.layoutManager = LinearLayoutManager(context)
//                val interestAdapter = InterestAdapter(context, cardInfo.member.interests)
//                binding.interestRecyclerView.adapter = interestAdapter

                // 내 관심사 목록
//                binding.interestRecyclerView2.layoutManager = LinearLayoutManager(context)
//                val interestAdapter2 = InterestAdapter(context, myInfo.interests)
//                binding.interestRecyclerView2.adapter = interestAdapter2

//                // 직사각형 차트
//                val rectangleChartView: RectangleChartView = binding.rectangleChartView
//                rectangleChartView.setInterests(myInfo.interests, cardInfo.member.interests)

                // 리싸이클러
                binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                val interestChartAdapter = InterestChartAdapter(context, cardInfo.member.interests) { interest ->
                    interestDescription.text = interest.interestName
                }
//                interestChartAdapter.setTotalWidth(binding.recycler.layoutParams.width)
//                binding.recycler.adapter = interestChartAdapter

                binding.recycler.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        // RecyclerView의 너비가 측정되었을 때 호출
                        val width = binding.recycler.width
                        interestChartAdapter.setTotalWidth(width)

                        // 레이아웃 리스너 제거
                        binding.recycler.viewTreeObserver.removeOnGlobalLayoutListener(this)

                        binding.recycler.adapter = interestChartAdapter
                    }
                })

                // 리싸이클러2
                binding.recycler2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val interestChartAdapter2 = InterestChartAdapter(context, myInfo.interests) { interest ->
                    interestDescription.text = interest.interestName
                }

                binding.recycler2.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        // RecyclerView의 너비가 측정되었을 때 호출
                        val width = binding.recycler2.width
                        interestChartAdapter2.setTotalWidth(width)

                        // 레이아웃 리스너 제거
                        binding.recycler2.viewTreeObserver.removeOnGlobalLayoutListener(this)

                        binding.recycler2.adapter = interestChartAdapter2
                    }
                })

                // Interest 데이터 합치기
                val combinedList = mutableListOf<CombinedInterest>()

                cardInfo.member.interests.forEach { roommateInterest ->
                    val myInterest = myInfo.interests.find { it.interestName == roommateInterest.interestName }
                    combinedList.add(
                        CombinedInterest(
                            roommateInterest.interestName,
                            roommateInterest.interestPercent,
                            myInterest?.interestPercent
                        )
                    )
                }

                myInfo.interests.filter { myInterest ->
                    cardInfo.member.interests.none { it.interestName == myInterest.interestName }
                }.forEach { combinedInterest ->
                    combinedList.add(
                        CombinedInterest(
                            combinedInterest.interestName,
                            null,
                            combinedInterest.interestPercent
                        )
                    )
                }

                val interestTableAdapter = InterestTableItemAdapter(context, combinedList) { combinedInterest ->
                    interestDescription.text = combinedInterest.interestName
                }
                interestTableRecyclerItem.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                interestTableRecyclerItem.adapter = interestTableAdapter

                interestTableAdapter.selectFirstItem()

                var selectedCombinedInterest = interestTableAdapter.getSelectedItem()?.interestName
                if (selectedCombinedInterest != null) {
                    interestDescription.text = selectedCombinedInterest
                    interestChartAdapter.selectInterestByName(selectedCombinedInterest)
                    interestChartAdapter2.selectInterestByName(selectedCombinedInterest)
                }
                var selectedRoommateInterest = interestChartAdapter.getSelectedItem()?.interestName
                if (selectedRoommateInterest != null) {
                    interestDescription.text = selectedRoommateInterest
                    interestChartAdapter.selectInterestByName(selectedRoommateInterest)
                    interestChartAdapter2.selectInterestByName(selectedRoommateInterest)
                }
                var selectedMyInterest = interestChartAdapter2.getSelectedItem()?.interestName
                if (selectedMyInterest != null) {
                    interestDescription.text = selectedMyInterest
                    interestChartAdapter.selectInterestByName(selectedMyInterest)
                    interestChartAdapter2.selectInterestByName(selectedMyInterest)
                }

                if (combinedList.isNotEmpty()) {
                    interestDescription.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            // 선택한 관심사로 레이아웃 변경
                            interestTableAdapter.selectInterestByName(interestDescription.text.toString())
                            interestChartAdapter.selectInterestByName(interestDescription.text.toString())
                            interestChartAdapter2.selectInterestByName(interestDescription.text.toString())

                            if (interestDescription.text.toString() == combinedList[0].interestName) {
                                matchInterestDescription.text = "가장 잘 맞는 관심사는 ${getInterestName(interestDescription.text.toString())}! ${getRandomHappyEmoji()}"
                            } else if (interestFinder(combinedList, interestDescription.text.toString()) == "both") {
                                matchInterestDescription.text = "둘 다 ${getInterestName(interestDescription.text.toString())} 좋아해요! ${getRandomHappyEmoji()}"
                            } else if (interestFinder(combinedList, interestDescription.text.toString()) == "me only") {
                                matchInterestDescription.text = "나만 ${getInterestName(interestDescription.text.toString())} 좋아해요 ${getRandomSadEmoji()}"
                            } else if (interestFinder(combinedList, interestDescription.text.toString()) == "roommate only") {
                                matchInterestDescription.text = "${cardInfo.member.memberNickname}만 ${getInterestName(interestDescription.text.toString())} 좋아해요 ${getRandomSadEmoji()}"
                            }
                            // 리싸이클러뷰 스크롤 이동
                            interestTableRecyclerItem.smoothScrollToPosition(interestPositionFinder(combinedList, interestDescription.text.toString()))
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }
                    })

                    matchInterestDescription.text = "가장 잘 맞는 관심사는 ${getInterestName(combinedList[0].interestName)}! ${getRandomHappyEmoji()}"

                }



                fixedLayout.setOnClickListener {
                    if (binding.hiddenView.visibility == View.VISIBLE){
                        TransitionManager.beginDelayedTransition(binding.characterViewGroup,
                            AutoTransition()
                        )
                        binding.hiddenView.visibility = View.GONE


                        characterDescriptionButton.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)
                    } else{
                        TransitionManager.beginDelayedTransition(binding.characterViewGroup,
                            AutoTransition())

                        binding.hiddenView.visibility = View.VISIBLE
                        characterDescriptionButton.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)
                    }
                }

                // 수면 차트
//                val sleepChart = binding.sleepChartView
//                sleepChart.setSleepTime(cardInfo.member.characteristic.sleepAt, cardInfo.member.characteristic.wakeUpAt)
//                binding.sleepTimeText.text = cardInfo.member.characteristic.sleepAt
//                binding.wakeTimeText.text = cardInfo.member.characteristic.wakeUpAt

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
                    Log.d("memberId", "$cardInfo")
                    // 채팅방 생성 요청
                    CoroutineScope(Dispatchers.IO).launch {
                        val apiService = NetworkModule.provideRetrofit(context)
                        val response = apiService.createChatRoom(cardInfo.member.memberId)
                        Log.d("memberId","$response")

                        withContext(Dispatchers.Main) {
                            when (response.body()?.code) {
                                201 -> {
                                    // 채팅방 생성 성공
                                    val intent = Intent(context, ChatActivity::class.java)
                                    intent.putExtra("memberId", cardInfo.member.memberId)
                                    Log.d("memberId", "생성 성공")
                                    context.startActivity(intent)
                                }
                                400 -> {
                                    // 채팅방 이미 존재
                                    Log.d("ChatRoomCreateFail", "Response code: ${response.code()}, Error body: ${response.errorBody()?.string()}")
                                    Toast.makeText(context, "이미 존재하는 채팅방입니다.", Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    // 채팅방 생성 실패
                                    Log.d("ChatRoomCreateFail", "Response code: ${response.code()}, Error body: ${response.errorBody()?.string()}")
                                    Toast.makeText(context, "채팅방 생성 실패: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
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
        fun bind(cardInfo: MatchedMemberData) {
            binding.apply {

                // 상대방 정보 바인딩
                val matchRateResult = (cardInfo.matchRate.toFloat() / 100).toInt()
                matchRate.text = "$matchRateResult"
                nickname.text = cardInfo.member.memberNickname
                location.text = "${cardInfo.member.metropolitanName}, ${cardInfo.member.cityName}"
                // 상대방 프로필 이미지 불러오기
                Glide.with(binding.root.context)
                    .load(cardInfo.member.memberProfileImageUrl)
                    .placeholder(R.drawable.sample_profile1)
                    .into(binding.profileImage)

                // 매칭률에 따라서 색상 변경
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
                    intent.putExtra("memberId", cardInfo.member.memberId)

                    // 채팅방 생성 요청
                    CoroutineScope(Dispatchers.IO).launch {
                        val apiService = NetworkModule.provideRetrofit(context)
                        val response = apiService.createChatRoom(cardInfo.member.memberId)

                        if (response.isSuccessful) {
                            // 채팅방 생성 성공
                            withContext(Dispatchers.Main) {
                                context.startActivity(intent)
                            }
                        } else {
                            // 채팅방 생성 실패
                            // 에러 처리를 여기서 하세요.
                            // 예: Toast 메시지를 출력하거나, 로그를 기록하는 등
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "채팅방 생성 실패: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
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

    fun interestFinder(combinedList: List<CombinedInterest>, searchName: String): String {
        val interest = combinedList.find { it.interestName == searchName }
        return when {
            interest != null -> {
                when {
                    interest.roommateInterestPercent != null && interest.myInterestPercent != null -> "both"
                    interest.roommateInterestPercent == null && interest.myInterestPercent != null -> "me only"
                    interest.roommateInterestPercent != null && interest.myInterestPercent == null -> "roommate only"
                    else -> "none"
                }
            }
            else -> "none"
        }
    }

    fun interestPositionFinder(combinedList: List<CombinedInterest>, searchName: String): Int {
        val interest = combinedList.indexOfFirst { it.interestName == searchName }
        if (interest > 0 && interest < combinedList.size) {
            return interest
        } else {
            return 0
        }
    }

    fun getRandomSadEmoji(): String {
        val unicodeEmojis = listOf(
            "\uD83E\uDD72", // U+1F972
            "\uD83D\uDE35", // U+1F635
            "\uD83D\uDE30", // U+1F630
            "\uD83D\uDE2D", // U+1F62D
            "\uD83D\uDE25", // U+1F625
            "\uD83D\uDE1E", // U+1F61E
            "\uD83E\uDD7A"
        )

        return unicodeEmojis.random()
    }

    fun getRandomHappyEmoji(): String {
        val unicodeEmojis = listOf(
            "\uD83D\uDE01", // U+1F601
            "\uD83D\uDE04", // U+1F604
            "\uD83D\uDE07", // U+1F607
            "\uD83D\uDE06", // U+1F606
            "\uD83D\uDE09", // U+1F609
            "\uD83D\uDE0A", // U+1F60A
            "\uD83D\uDE0B", // U+1F60B
            "\uD83D\uDE0C", // U+1F60C
            "\uD83D\uDE0D", // U+1F60D
            "\uD83D\uDE1D"  // U+1F61D
        )

        return unicodeEmojis.random()
    }
}
