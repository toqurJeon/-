package com.example.moiroom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.moiroom.R

class SleepChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var sleepAt = "23:00"
    private var wakeUpAt = "08:00"

    private var sleepTimeDegree = 0f
    private var wakeUpTimeDegree = 0f

    private var circlePaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.BGgray)
    }

    private var sleepPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.sleepIndigo)
    }

    private var innerPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.white)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        // 최소 지름을 계산합니다. (너비와 높이 중 작은 값)
        val minDiameter = Math.min(widthSize, heightSize)

        // 뷰의 크기를 설정합니다. 반원의 크기에 맞게 설정합니다.
        val newWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else minDiameter
        val newHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else minDiameter

        // 최종적으로 뷰의 크기를 설정합니다.
        setMeasuredDimension(newWidth, newHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        calculateDegree()

        Log.d("MMMMMMMMMMMMmYTAG", "onDraw: $sleepTimeDegree, $wakeUpTimeDegree") // 352.5, 240.0

        // 반원 그리기
        canvas.drawArc(0f, 0f, width.toFloat(), height.toFloat(), 180f, 180f, true, circlePaint)

        // 잠에 들어있는 시간 색칠하기
        if (sleepTimeDegree > wakeUpTimeDegree) {
            // 잠든 시간이 깬 시간보다 뒤에 있는 경우
            canvas.drawArc(0f, 0f, width.toFloat(), height.toFloat(), 180f, wakeUpTimeDegree - 180, true, sleepPaint)
            canvas.drawArc(0f, 0f, width.toFloat(), height.toFloat(), sleepTimeDegree, 360f - sleepTimeDegree, true, sleepPaint)
        } else {
            // 잠든 시간이 깬 시간보다 앞에 있는 경우
            canvas.drawArc(0f, 0f, width.toFloat(), height.toFloat(), sleepTimeDegree, wakeUpTimeDegree - sleepTimeDegree, true, sleepPaint)
        }
        canvas.drawArc(
            width.toFloat() / 8, height.toFloat() / 8,  // left, top
            (width.toFloat() / 8) * 7, height.toFloat() / 8 * 7,  // right, bottom
            0f, 360f, true, innerPaint
        )

    }


    fun setSleepTime(sleepAt: String, wakeUpAt: String) {
        this.sleepAt = sleepAt
        this.wakeUpAt = wakeUpAt

        calculateDegree()
        invalidate()
    }

    private fun calculateDegree() {
        val sleepAtSplit = sleepAt.split(":").map { it.toInt() }
        val wakeUpAtSplit = wakeUpAt.split(":").map { it.toInt() }

        sleepTimeDegree = ((sleepAtSplit[0] + sleepAtSplit[1] / 60f) / 24f * 180f + 180f) % 360
        wakeUpTimeDegree = ((wakeUpAtSplit[0] + wakeUpAtSplit[1] / 60f) / 24f * 180f + 180f) % 360
    }
}
