package com.example.moiroom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.moiroom.R
import com.example.moiroom.data.Interest
import com.example.moiroom.utils.getColorInterest

class SquareChartView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var data: List<Interest> = mutableListOf()

    fun setData(data: List<Interest>) {
        this.data = data
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSquares(canvas)
    }

    private fun drawSquares(canvas: Canvas) {
        val rowCount = 10
        val columnCount = 10
        val totalSquares = rowCount * columnCount

        // 각 정사각형 사이의 간격 설정
        val space = width / (columnCount * 12) // (정사각형 개수 + 1) * 간격 개수 (좌우 간격이 모두 있어야 하므로)

        val squareSize = (width - (columnCount + 1) * space) / columnCount // 간격을 고려한 정사각형의 실제 크기 계산

        var currentX = space // 시작 위치에 간격을 추가
        var currentY = space // 시작 위치에 간격을 추가

        var dataIndex = 0 // 데이터 리스트 인덱스

        // 정사각형 그리기
        for (row in 0 until rowCount) {
            for (col in 0 until columnCount) {
                if (dataIndex >= data.size) return // 데이터 인덱스가 데이터 리스트 크기를 넘어가면 그리기를 중단합니다.

                val interest = data[dataIndex]
                val numOfSquares = interest.interestPercent

                val color = getColorInterest(interest.interestName, context)

                val paint = Paint().apply {
                    this.color = color
                    style = Paint.Style.FILL
                }

                // 정사각형 그리기
                repeat(numOfSquares) {
                    val rectF = RectF(
                        currentX.toFloat(),
                        currentY.toFloat(),
                        (currentX + squareSize).toFloat(),
                        (currentY + squareSize).toFloat()
                    )
                    canvas.drawRoundRect(rectF, 16f, 16f, paint)

                    // 다음 정사각형의 위치로 이동
                    currentX += squareSize + space

                    // 만약 다음 열로 넘어가야 하는 경우
                    if (currentX + squareSize > width - space) {
                        currentX = space // 시작 위치에 간격을 추가
                        currentY += squareSize + space
                    }
                }

                dataIndex++
            }
        }

    }
}
