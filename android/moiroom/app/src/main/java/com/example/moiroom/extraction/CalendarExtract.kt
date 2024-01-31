package com.example.moiroom.extraction

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CallLog
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moiroom.data.CalendarEvent
import com.example.moiroom.data.CallLogItem
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.kakao.sdk.common.KakaoSdk.type

class CalendarExtract: AppCompatActivity() {
    private lateinit var binding: ActivityJaeeontestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        Log.d("함수 실행", "함수 실행")
        val events = getCalendarEvent(this)
        Log.d("사진들", "$events")
        binding.textview.text = "${events}"
    }

    fun getCalendarEvent(context: Context): List<CalendarEvent> {
        val calendarEventList = mutableListOf<CalendarEvent>()


        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            null  // 날짜 기준으로 내림차순 정렬
        )

        // 쿼리 결과를 가지고 작업
        cursor?.use {
            val a = CalendarContract.Calendars.ACCOUNT_TYPE
//            val b = if (a != -1) it.getString(a) else ""
            Log.d("캘린더","$a")
            val eventIdIndex = it.getColumnIndex(CalendarContract.Events._ID)
            val calendarIdIndex = it.getColumnIndex(CalendarContract.Events.CALENDAR_ID)
            val titleIndex = it.getColumnIndex(CalendarContract.Events.TITLE)
            val descriptionIndex = it.getColumnIndex(CalendarContract.Events.DESCRIPTION)
            val eventLocationIndex = it.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)
            val startTimeIndex = it.getColumnIndex(CalendarContract.Events.DTSTART)
            val endTimeIndex = it.getColumnIndex(CalendarContract.Events.DTEND)
            val durationIndex = it.getColumnIndex(CalendarContract.Events.ALL_DAY)
            val isAllDayIndex = it.getColumnIndex(CalendarContract.Events.EVENT_TIMEZONE)
            val eventTimezoneIndex = it.getColumnIndex(CalendarContract.Events.RRULE)
            val recurrenceRuleIndex = it.getColumnIndex(CalendarContract.Events.RDATE)
            val recurrenceDateIndex = it.getColumnIndex(CalendarContract.Events.ORGANIZER)
            val organizerIndex = it.getColumnIndex(CalendarContract.Events.GUESTS_CAN_MODIFY)
            val accessLevelIndex = it.getColumnIndex(CalendarContract.Events.ACCESS_LEVEL)
            val availabilityIndex = it.getColumnIndex(CalendarContract.Events.AVAILABILITY)



            while (it.moveToNext()) {
                val eventId = if (eventIdIndex != -1) it.getLong(eventIdIndex) else 0
                val calendarId = if (calendarIdIndex != -1) it.getLong(calendarIdIndex) else 0
                val title = if (titleIndex != -1) it.getString(titleIndex) else ""
                val description = if (descriptionIndex != -1) it.getString(descriptionIndex) else ""
                val eventLocation = if (eventLocationIndex != -1) it.getString(eventLocationIndex) else ""
                val startTime = if (startTimeIndex != -1) it.getLong(startTimeIndex) else 0
                val endTime = if (endTimeIndex != -1) it.getLong(endTimeIndex) else 0
                val duration = if (durationIndex != -1) it.getString(durationIndex) else ""
                val isAllDay = if (isAllDayIndex != -1) it.getInt(isAllDayIndex) else 0
                val eventTimezone = if (eventTimezoneIndex != -1) it.getString(eventTimezoneIndex) else ""
                val recurrenceRule = if (recurrenceRuleIndex != -1) it.getString(recurrenceRuleIndex) else ""
                val recurrenceDate = if (recurrenceDateIndex != -1) it.getString(recurrenceDateIndex) else ""
                val organizer = if (organizerIndex != -1) it.getString(organizerIndex) else ""
                val accessLevel = if (accessLevelIndex != -1) it.getInt(accessLevelIndex) else 0
                val availability = if (availabilityIndex != -1) it.getInt(availabilityIndex) else 0

                // CallLogItem 객체를 만들어 리스트에 추가
                val calendarEventItem = CalendarEvent(
                    eventId,
                    calendarId,
                    title,
                    description,
                    eventLocation,
                    startTime,
                    endTime,
                    duration,
                    isAllDay,
                    eventTimezone,
                    recurrenceRule,
                    recurrenceDate,
                    organizer,
                    accessLevel,
                    availability)
//                if (title != "") {
                calendarEventList.add(calendarEventItem)
//                }
            }
        }

        // 리스트 반환
        return calendarEventList
    }
}
