package com.example.moiroom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val dot1 = findViewById<View>(R.id.loading_dot1)
        val dot2 = findViewById<View>(R.id.loading_dot2)
        val dot3 = findViewById<View>(R.id.loading_dot3)

        val dotAnim1 = AnimationUtils.loadAnimation(this, R.anim.dot_scale)
        val dotAnim2 = AnimationUtils.loadAnimation(this, R.anim.dot_scale).apply { startOffset = 200L }
        val dotAnim3 = AnimationUtils.loadAnimation(this, R.anim.dot_scale).apply { startOffset = 400L }

        dot1.startAnimation(dotAnim1)
        dot2.startAnimation(dotAnim2)
        dot3.startAnimation(dotAnim3)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}
