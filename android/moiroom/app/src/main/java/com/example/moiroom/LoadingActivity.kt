package com.example.moiroom

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val dot1 = findViewById<View>(R.id.dot_1)
        val dot2 = findViewById<View>(R.id.dot_2)
        val dot3 = findViewById<View>(R.id.dot_3)
        val dot4 = findViewById<View>(R.id.dot_4)
        val dot5 = findViewById<View>(R.id.dot_5)

        val animationDuration = 2000L

        val animator1 = getAnimator(dot1, animationDuration, 0)
        val animator2 = getAnimator(dot2, animationDuration, animationDuration / 5)
        val animator3 = getAnimator(dot3, animationDuration, animationDuration * 2 / 5)
        val animator4 = getAnimator(dot4, animationDuration, animationDuration * 3 / 5)
        val animator5 = getAnimator(dot5, animationDuration, animationDuration * 4 / 5)

        animator1.start()
        animator2.start()
        animator3.start()
        animator4.start()
        animator5.start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }

    private fun getAnimator(view: View, duration: Long, delay: Long): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "alpha", 0.2f, 1f, 0.2f).apply {
            this.duration = duration
            repeatCount = ValueAnimator.INFINITE
            startDelay = delay
        }
    }
}
