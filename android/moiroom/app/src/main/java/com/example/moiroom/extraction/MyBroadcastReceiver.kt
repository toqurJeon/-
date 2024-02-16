package com.example.moiroom.extraction

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // "myapp://" 스킴을 처리하는 로직을 여기에 추가
        if (intent != null && Intent.ACTION_VIEW == intent.action) {
            val data: Uri? = intent.data
            if (data != null && "https://example.com/instagramredirection" == data.scheme) {
                // "myapp://" 스킴으로 들어온 경우에 대한 처리
                // 예: 특정 화면으로 이동하거나 특정 동작 수행
                Log.d("브로드캐스트", "브로드캐스트")
            }
        }
    }
}