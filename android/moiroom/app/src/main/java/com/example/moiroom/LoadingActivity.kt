package com.example.moiroom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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

        dot1.startAnimation(getDotAnimation(0L))
        dot2.startAnimation(getDotAnimation(200L))
        dot3.startAnimation(getDotAnimation(400L))
        dot4.startAnimation(getDotAnimation(600L))
        dot5.startAnimation(getDotAnimation(800L))

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish()
        }, 10000)
    }

    private fun getDotAnimation(offset: Long): Animation {
        return AnimationUtils.loadAnimation(this, R.anim.dot_scale).also {
            it.startOffset = offset
        }
    }
}
