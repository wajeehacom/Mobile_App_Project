package com.example.financepal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class SemiCircularPieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    private var data: List<Float> = listOf()
    private var colors: List<Int> = listOf()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (data.isEmpty() || colors.isEmpty()) return

        val oval = RectF(50f, 50f, width.toFloat() - 50, height.toFloat() - 50)
        val total = data.sum()

        if (total == 0f) return

        var startAngle = 180f

        for (i in data.indices) {
            paint.color = colors[i]
            val sweepAngle = data[i] * 180 / total
            canvas.drawArc(oval, startAngle, sweepAngle, true, paint)

            // Calculate the middle angle for placing the text
            val middleAngle = startAngle + sweepAngle / 2
            val x = (oval.centerX() + Math.cos(Math.toRadians(middleAngle.toDouble())) * oval.width() / 2.5).toFloat()
            val y = (oval.centerY() + Math.sin(Math.toRadians(middleAngle.toDouble())) * oval.height() / 2.5).toFloat()

            // Draw the percentage text
            val percentage = (data[i] / total * 100).toInt()
            canvas.drawText("$percentage%", x, y, textPaint)

            startAngle += sweepAngle
        }
    }


    fun setData(newData: List<Float>) {
        data = newData
        invalidate()   //Calls the onDraw() method for re-draw of the view
    }

    fun setColors(newColors: List<Int>) {
        colors = newColors
        invalidate()
    }
}
