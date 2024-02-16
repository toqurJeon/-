package com.example.moiroom.extraction

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moiroom.data.CallLogItem
import android.provider.CallLog.Calls
import android.provider.CallLog.Locations
import android.util.Log
import com.example.moiroom.NowMatchingActivity
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CallExtract: AppCompatActivity() {
    private lateinit var binding: ActivityJaeeontestBinding
    var calldata = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        // 갤러리에서 모든 사진 가져오기
//        val calls = getCallLog(this).reversed().subList(1, 4)
        val calls = getCallLog(this)
        postFuel(calls)
//        binding.textview.text = "$calls"
    }
    fun callExtract() {
        val calls = getCallLog(this)
        return postFuel(calls)
    }


    fun getCallLog(context: Context): String {
        val callLogList = mutableListOf<CallLogItem>()
        val stringBuilder = StringBuilder()
        stringBuilder.append(" \"calls\": [")

        // ContentResolver를 사용하여 CallLog.Calls에 쿼리를 수행
        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver.query(
            Calls.CONTENT_URI,
            null,
            null,
            null,
            "${Calls.DATE} DESC"  // 날짜 기준으로 내림차순 정렬
        )

        // 쿼리 결과를 가지고 작업
        cursor?.use {
            val numberIndex = it.getColumnIndex(Calls.NUMBER)
            val nameIndex = it.getColumnIndex(Calls.CACHED_NAME)
            val dateIndex = it.getColumnIndex(Calls.DATE)
            val durationIndex = it.getColumnIndex(Calls.DURATION)
            val typeIndex = it.getColumnIndex(Calls.TYPE)
            val locationIndex = it.getColumnIndex(Calls.GEOCODED_LOCATION)
            val Latitude = it.getColumnIndex(Locations.LATITUDE)
            val Longitude = it.getColumnIndex(Locations.LONGITUDE)
            var a = 1
            while (it.moveToNext()) {
                val number = if (numberIndex != -1) it.getString(numberIndex) else ""
                val date = if (dateIndex != -1) it.getLong(dateIndex) else 0
                val duration = if (durationIndex != -1) it.getInt(durationIndex) else 0
                val type = if (typeIndex != -1) it.getInt(typeIndex) else 0
                val location = if (locationIndex != -1) it.getString(locationIndex) else ""
                val name = if (nameIndex != -1) it.getString(nameIndex) else ""

                if (number.startsWith("010")) {
                    stringBuilder.append("{ \"number\": \"$number\", \"name\": \"$name\", \"date\": $date, \"duration\": $duration, \"type\": $type, \"location\": \"$location\"}, ")
                }
                a += 1
                if ( a > 300 ) {
                    break
                }
            }
        }
        if (stringBuilder.toString().last() != "["[0]) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        }
        stringBuilder.append("]")

        // 리스트 반환
        return stringBuilder.toString()
    }

    private fun postFuel(data: String) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("{ \"calls\": ")
//        stringBuilder.append(data)
        stringBuilder.append("$data")
        stringBuilder.append("}")
        // FuelManager 설정 (선택사항)http://www.moiroom.r-e.kr/call
        FuelManager.instance.basePath = "http://www.moiroom.r-e.kr"
        binding.textview.text = stringBuilder.toString()
        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/call")
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