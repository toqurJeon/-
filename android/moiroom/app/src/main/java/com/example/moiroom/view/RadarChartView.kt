package com.example.moiroom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.moiroom.data.CharacteristicType
import com.example.moiroom.data.RadarChartData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class RadarChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var dataList1: ArrayList<RadarChartData>? = null
    private var dataList2: ArrayList<RadarChartData>? = null

    // 5개의 특성을 갖도록 한다
    private var chartTypes = arrayListOf(
        CharacteristicType.sociability,
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
    private val labelTextSizeInSp: Float = 16f

    val myTypeface = context?.assets?.let { Typeface.createFromAsset(it, "pretendard_semibold.ttf") }


    private val textPaint = TextPaint().apply {
        textSize = dataGuideTextSizeInSp * scaledDensity
        textAlign = Paint.Align.CENTER
        typeface = myTypeface
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
        val radian = PI.toFloat() * 2 / 8 // 360도를 8분할한 각만큼 회전시키 위해
        val step = 2 // 데이터 가이드 라인은 5단계로 표시한다
        val heightMaxValue = height / 2 * 0.7f // RadarChartView영역내에 모든 그림이 그려지도록 max value가 그려질 높이
        val heightStep = heightMaxValue / step // 1단계에 해당하는 높이
        val cx = width / 2f
        val cy = height / 2f
        // 1. 단계별 가이드라인(8각형) 그리기
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
//            if (i < step) {
//                val strValue = "${100 - 50 * i}"
//                textPaint.textAlign = Paint.Align.LEFT
//                textPaint.color = Color.GRAY
//                canvas.drawText(
//                    strValue,
//                    startX + 10,
//                    textPaint.fontMetrics.getBaseLine(startY),
//                    textPaint
//                )
//            }
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
        val marginInDp = 368f
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
            dataList1?.firstOrNull { it.type == type }?.value?.let { value ->
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
            color = Color.parseColor("#1AFF8A00")
        }

        // 4. 데이터 표시
        path.close()

        canvas.drawPath(path, fillPaint)
        canvas.drawPath(path, strokePaint)

        if (dataList2 != null) {
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
                dataList2?.firstOrNull { it.type == type }?.value?.let { value ->
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
                color = Color.parseColor("#28E016")
            }
            val fillPaint = Paint().apply {
                style = Paint.Style.FILL
                color = Color.parseColor("#1A28E016")
            }

            // 4. 데이터 표시
            path.close()

            canvas.drawPath(path, fillPaint)
            canvas.drawPath(path, strokePaint)
        }

        // paint.color = 0x7FFF8A00
        // paint.style = Paint.Style.FILL_AND_STROKE
        // canvas.drawPath(path, paint)
    }

    fun setDataList(dataList1: ArrayList<RadarChartData>, dataList2: ArrayList<RadarChartData>?) {
        if (dataList1.isEmpty()) {
            return
        }
        this.dataList1 = dataList1
        this.dataList2 = dataList2
        invalidate()
    }

    // 점(x, y)를 특정 좌표(cx, cy)를 중심으로 radian만큼 회전시킨 점의 좌표를 반환
    private fun transformRotate(radian: Float, x: Float, y: Float, cx: Float, cy: Float): PointF {
        val stopX = cos(radian) * (x - cx) - sin(radian) * (y - cy) + cx
        val stopY = sin(radian) * (x - cx) + cos(radian) * (y - cy) + cy

        return PointF(stopX, stopY)
    }
}