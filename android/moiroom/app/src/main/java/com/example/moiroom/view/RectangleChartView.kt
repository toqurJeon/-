package com.example.moiroom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.moiroom.data.Interest
import com.example.moiroom.utils.getColorInterest

class RectangleChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var interestsList1: List<Interest> = emptyList()
    private var interestsList2: List<Interest> = emptyList()

    fun setInterests(interestsList1: List<Interest>, interestsList2: List<Interest>) {
        this.interestsList1 = interestsList1
        this.interestsList2 = interestsList2
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val chartHeight = height.toFloat() / 3
        val totalWidth = width.toFloat()

        drawChart(canvas, interestsList1, 0f, chartHeight, totalWidth)
        // Add a gap between the charts
        val gapHeight = chartHeight / 2
        drawChart(canvas, interestsList2, chartHeight + gapHeight, chartHeight, totalWidth)
    }

    private fun drawChart(canvas: Canvas, interests: List<Interest>, startY: Float, chartHeight: Float, totalWidth: Float) {
        var currentX = 0f

        interests.forEach { interest ->
            val width = totalWidth * (interest.interestPercent.toFloat() / 100f)
            val paint = Paint().apply {
                color = getColorInterest(interest.interestName, context)
            }
            canvas.drawRect(currentX, startY, currentX + width, startY + chartHeight, paint)
            currentX += width
        }
    }
}


