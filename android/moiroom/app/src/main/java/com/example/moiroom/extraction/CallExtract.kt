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
import com.example.moiroom.databinding.ActivityJaeeontestBinding

class CallExtract: AppCompatActivity() {
    private lateinit var binding: ActivityJaeeontestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        Log.d("함수 실행", "함수 실행")
        // 갤러리에서 모든 사진 가져오기
//        val calls = getCallLog(this).reversed().subList(1, 4)
        val calls = getCallLog(this)
        Log.d("사진들", "$calls")
        binding.textview.text = "$calls"
    }

    fun getCallLog(context: Context): List<CallLogItem> {
        val callLogList = mutableListOf<CallLogItem>()

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
            val dateIndex = it.getColumnIndex(Calls.DATE)
            val durationIndex = it.getColumnIndex(Calls.DURATION)
            val typeIndex = it.getColumnIndex(Calls.TYPE)
            val locationIndex = it.getColumnIndex(Calls.GEOCODED_LOCATION)
            val Latitude = it.getColumnIndex(Locations.LATITUDE)
            val Longitude = it.getColumnIndex(Locations.LONGITUDE)

            while (it.moveToNext()) {
                val number = if (numberIndex != -1) it.getString(numberIndex) else ""
                val date = if (dateIndex != -1) it.getLong(dateIndex) else 0
                val duration = if (durationIndex != -1) it.getInt(durationIndex) else 0
                val type = if (typeIndex != -1) it.getInt(typeIndex) else 0
                val location = if (locationIndex != -1) it.getString(locationIndex) else ""

                // CallLogItem 객체를 만들어 리스트에 추가
                val callLogItem = CallLogItem(number, date, duration, type, location, Latitude, Longitude)
                callLogList.add(callLogItem)
            }
        }

        // 리스트 반환
        return callLogList
    }
}