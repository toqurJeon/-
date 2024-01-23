package com.example.moiroom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val progressBar: ProgressBar = findViewById(R.id.progress_circular)
        progressBar.visibility = View.VISIBLE  // ProgressBar를 보이게 합니다.

        // Schedule a Runnable to be executed after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Start NaviActivity
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            progressBar.visibility = View.GONE  // ProgressBar를 숨깁니다.
            // Optionally, you can add this line to close the current activity
            // finish()
        }, 2500)  // 5000 milliseconds = 5 seconds
    }
}
