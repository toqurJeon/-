package com.example.moiroom.extraction

import android.app.usage.NetworkStats
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.moiroom.R
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar


class AppExtract: AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
//    }
    private lateinit var binding: ActivityJaeeontestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appExtract()
    }

    fun appExtract() {
        // UsageStatsManager를 사용하여 앱 사용 통계 데이터를 가져올 수 있음
        val usageStatsList = getUsageStatsForPeriod()
        val stringBuilder = StringBuilder()
        stringBuilder.append("[")
        for (usageStats in usageStatsList) {
            val packageName = usageStats.packageName
            val totalUsageTime = usageStats.totalTimeInForeground
            stringBuilder.append("{ \"packageName\": \"$packageName\", \"totalUsageTime\": ${totalUsageTime} }, ")
            // 사용량 관련 작업 수행
        }
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        stringBuilder.append("]")
        postFuel(stringBuilder.toString())
    }


    private fun getUsageStatsForPeriod(): List<UsageStats> {
        val calendar = Calendar.getInstance()
        // 현재 날짜로부터 일주일 전의 날짜로 설정
        calendar.add(Calendar.WEEK_OF_YEAR, -1)
        val startMillis = calendar.timeInMillis
        val endMillis = System.currentTimeMillis()

        val usageStatsManager = this.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_WEEKLY,
            startMillis,
            endMillis
        )
    }

    private fun postFuel(data: String) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("{ \"apps\": ")
//        stringBuilder.append(data)
        stringBuilder.append("$data")
        stringBuilder.append("}")
        // FuelManager 설정 (선택사항)
        FuelManager.instance.basePath = "http://i10a308.p.ssafy.io:5000"
        Log.d("최종 전송 데이터", stringBuilder.toString())
        binding.textview.text = stringBuilder.toString()
        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/calling_history")
                    .header("Content-Type" to "application/json")
                    .jsonBody(
                        stringBuilder.toString()
                    )
                    .responseString()

                // 응답 확인
                response.third.fold(
                    success = { data ->
                        Log.d("서버 응답", "$data")
                    },
                    failure = { error -> Log.d("서버 에러", "에러: $error") }
                )
            } catch (e: Exception) {
                println("에러: $e")
            }
        }
    }
}
