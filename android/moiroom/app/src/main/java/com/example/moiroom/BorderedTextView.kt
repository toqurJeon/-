package com.example.moiroom

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

class BorderedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        // Initialize border when creating the TextView
        initBorder()
    }

    private fun initBorder() {
        val borderColor = 0xFFFF960B.toInt() // Set your desired border color
        val borderWidth = 6 // Set your desired border width in pixels
        val borderRadius = 128f // Set your desired border radius in pixels

        // Create a GradientDrawable with a stroke (border) and rounded corners
        val borderDrawable = createBorderDrawable(borderColor, borderWidth, borderRadius)

        // Set the background to the created drawable
        setBackgroundDrawableCompat(borderDrawable)
    }

    private fun createBorderDrawable(@ColorInt borderColor: Int, borderWidth: Int, borderRadius: Float): Drawable {
        val borderDrawable = GradientDrawable()
        borderDrawable.shape = GradientDrawable.RECTANGLE
        borderDrawable.setColor(0) // Transparent background
        borderDrawable.setStroke(borderWidth, borderColor)
        borderDrawable.cornerRadius = borderRadius

        return borderDrawable
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setBackgroundDrawableCompat(drawable: Drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = drawable
        } else {
            setBackgroundDrawable(drawable)
        }
    }
}
