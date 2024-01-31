package com.example.moiroom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.moiroom.data.Interest

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
        val squareSize = width / 10 // 각 정사각형의 크기 계산

        var currentX = 0 // 현재 x 좌표 초기화
        var currentY = 0 // 현재 y 좌표 초기화

        for (interest in data) {
            val squareCount = interest.interestPercent // 해당 항목의 퍼센트
            val color = getRandomColor() // 랜덤한 색상 가져오기

            val paint = Paint().apply {
                this.color = color
                style = Paint.Style.FILL
            }

            repeat(squareCount) {
                // 현재 x, y 좌표에서 정사각형 그리기
                canvas.drawRect(
                    currentX.toFloat(),
                    currentY.toFloat(),
                    (currentX + squareSize).toFloat(),
                    (currentY + squareSize).toFloat(),
                    paint
                )

                // 다음 정사각형의 x 좌표 계산
                currentX += squareSize
                // x 좌표가 뷰의 가로 길이를 벗어나면 다음 행으로 이동
                if (currentX >= width) {
                    currentX = 0
                    currentY += squareSize
                }
            }
        }
    }

    private fun getRandomColor(): Int {
        val r = (0..255).random()
        val g = (0..255).random()
        val b = (0..255).random()
        return Color.rgb(r, g, b)
    }
}
