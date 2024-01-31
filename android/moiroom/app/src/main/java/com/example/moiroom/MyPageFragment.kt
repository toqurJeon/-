package com.example.moiroom

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextPaint
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.moiroom.data.Interest
import com.example.moiroom.data.Member
import com.example.moiroom.databinding.FragmentMyPageBinding
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


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
                RadarChartData(CharacteristicType.socialbility, memberData.socialbility.toFloat()/100),
                RadarChartData(CharacteristicType.positivity, memberData.positivity.toFloat()/100),
                RadarChartData(CharacteristicType.activity, memberData.activity.toFloat()/100),
                RadarChartData(CharacteristicType.communion, memberData.communion.toFloat()/100),
                RadarChartData(CharacteristicType.altruism, memberData.altruism.toFloat()/100),
                RadarChartData(CharacteristicType.empathy, memberData.empathy.toFloat()/100),
                RadarChartData(CharacteristicType.humor, memberData.humor.toFloat()/100),
                RadarChartData(CharacteristicType.generous, memberData.generous.toFloat()/100),
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

    enum class CharacteristicType(val value: String) {
        socialbility("사교"),
        positivity("긍정"),
        activity("활동"),
        communion("공유"),
        altruism("이타"),
        empathy("공감"),
        humor("감각"),
        generous("관대")
    }

    data class RadarChartData(
        val type: CharacteristicType,
        val value: Float
    )

    class RadarChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

        private var dataList: ArrayList<RadarChartData>? = null

        // 5개의 특성을 갖도록 한다
        private var chartTypes = arrayListOf(
            CharacteristicType.socialbility,
            CharacteristicType.positivity,
            CharacteristicType.activity,
            CharacteristicType.communion,
            CharacteristicType.altruism,
            CharacteristicType.empathy,
            CharacteristicType.humor,
            CharacteristicType.generous
        )

        private val paint = Paint().apply {
            isAntiAlias = true
        }

        // 픽셀 단위의 텍스트 크기를 sp 단위의 텍스트 크기로 동적 설정
        private val scaledDensity: Float = resources.displayMetrics.scaledDensity
        private val dataGuideTextSizeInSp: Float = 14f // sp 단위의 텍스트 크기 지정
        private val labelTextSizeInSp: Float = 18f

        private val textPaint = TextPaint().apply {
            textSize = dataGuideTextSizeInSp * scaledDensity
            textAlign = Paint.Align.CENTER
            // typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        private var path = Path()

        private fun Paint.FontMetrics.getBaseLine(y: Float): Float {
            val halfTextAreaHeight = (bottom - top) / 2
            return y - halfTextAreaHeight - top
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas ?: return

            val strokeWidthInDp = 1f // 원하는 dp 크기로 설정
            val scale = resources.displayMetrics.density
            val strokeWidthInPixel = (strokeWidthInDp * scale + 0.5f).toInt() // dp를 픽셀로 변환
            paint.strokeWidth = strokeWidthInPixel.toFloat()


            paint.color = Color.LTGRAY
            paint.style = Paint.Style.STROKE
//            paint.strokeWidth = 4f
            val radian = PI.toFloat() * 2 / 8 // 360도를 5분할한 각만큼 회전시키 위해
            val step = 5 // 데이터 가이드 라인은 5단계로 표시한다
            val heightMaxValue = height / 2 * 0.7f // RadarChartView영역내에 모든 그림이 그려지도록 max value가 그려질 높이
            val heightStep = heightMaxValue / step // 1단계에 해당하는 높이
            val cx = width / 2f
            val cy = height / 2f
            // 1. 단계별 가이드라인(5각형) 그리기
            for (i in 0..step) {
                var startX = cx
                var startY = (cy - heightMaxValue) + heightStep * i
                repeat(chartTypes.size) {
                    // 중심좌표를 기준으로 점(startX,startY)를 radian만큼씩 회전시킨 점(stopX, stopY)을 계산한다.
                    val stopPoint = transformRotate(radian, startX, startY, cx, cy)
                    canvas.drawLine(startX, startY, stopPoint.x, stopPoint.y, paint)

                    startX = stopPoint.x
                    startY = stopPoint.y
                }

                // 각 단계별 기준값 표시
                if (i < step) {
                    val strValue = "${100 - 20 * i}"
                    textPaint.textAlign = Paint.Align.LEFT
                    textPaint.color = Color.GRAY
                    canvas.drawText(
                        strValue,
                        startX + 10,
                        textPaint.fontMetrics.getBaseLine(startY),
                        textPaint
                    )
                }
            }

            // 2. 중심으로부터 5각형의 각 꼭지점까지 잇는 라인 그리기
            var startX = cx
            var startY = cy - heightMaxValue
            repeat(chartTypes.size) {
                val stopPoint = transformRotate(radian, startX, startY, cx, cy)
                canvas.drawLine(cx, cy, stopPoint.x, stopPoint.y, paint)

                startX = stopPoint.x
                startY = stopPoint.y
            }

            // 3. 각 꼭지점 부근에 각 특성 문자열 표시하기

            // dp 단위로 마진 설정
            val marginInDp = 352f
            val marginInPixel = (marginInDp * scale).toInt() // dp를 픽셀로 변환

            textPaint.color = Color.BLACK
            textPaint.textSize = labelTextSizeInSp * scaledDensity
            textPaint.textAlign = Paint.Align.CENTER

            startX = cx
//            startY = (cy - heightMaxValue) * 0.5f // 값을 줄일수록 그래프와의 마진 높아짐
            startY = cy - heightMaxValue * (marginInPixel / height.toFloat())
            var r = 0f
            path.reset()
            chartTypes.forEach { type ->
                val point = transformRotate(r, startX, startY, cx, cy)
                canvas.drawText(
                    type.value,
                    point.x,
                    textPaint.fontMetrics.getBaseLine(point.y),
                    textPaint
                )

                // 전달된 데이터를 표시하는 path 계산
                dataList?.firstOrNull { it.type == type }?.value?.let { value ->
                    val conValue = heightMaxValue * value / 100 // 차트크기에 맞게 변환
                    val valuePoint = transformRotate(r, startX, cy - conValue, cx, cy)
                    if (path.isEmpty) {
                        path.moveTo(valuePoint.x, valuePoint.y)
                    } else {
                        path.lineTo(valuePoint.x, valuePoint.y)
                    }
                }

                r += radian
            }

            val strokePaint = Paint().apply {
                val outlineStrokeWidthInDp = 2f // 원하는 dp 크기로 설정

                style = Paint.Style.STROKE
                strokeWidth = outlineStrokeWidthInDp * scale
                color = Color.parseColor("#FF8A00")
            }
            val fillPaint = Paint().apply {
                style = Paint.Style.FILL
                color = Color.parseColor("#33FF8A00")
            }

            // 4. 데이터 표시
            path.close()

            canvas.drawPath(path, fillPaint)
            canvas.drawPath(path, strokePaint)

            // paint.color = 0x7FFF8A00
            // paint.style = Paint.Style.FILL_AND_STROKE
            // canvas.drawPath(path, paint)
        }

        fun setDataList(dataList: ArrayList<RadarChartData>) {
            if (dataList.isEmpty()) {
                return
            }
            this.dataList = dataList
            invalidate()
        }

        // 점(x, y)를 특정 좌표(cx, cy)를 중심으로 radian만큼 회전시킨 점의 좌표를 반환
        private fun transformRotate(radian: Float, x: Float, y: Float, cx: Float, cy: Float): PointF {
            val stopX = cos(radian) * (x - cx) - sin(radian) * (y - cy) + cx
            val stopY = sin(radian) * (x - cx) + cos(radian) * (y - cy) + cy

            return PointF(stopX, stopY)
        }
    }

    // y좌표가 중심이 오도록 문자열을 그릴수 있도록하는 baseline좌표를 반환
    fun Paint.FontMetrics.getBaseLine(y: Float): Float {
        val halfTextAreaHeight = (bottom - top) / 2
        return y - halfTextAreaHeight - top
    }
}