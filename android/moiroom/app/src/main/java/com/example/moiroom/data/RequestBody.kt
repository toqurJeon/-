package com.example.moiroom.data

import com.google.gson.annotations.SerializedName

data class RequestBody(
    val param1: Double,
    val param2: Double,
    val param3: Double
)

data class MyResponse(
    val message: String,
    val status: String
) {
    constructor() : this("", "")
}

data class PhotoInfo(
    val photoPath: String,
    val photoDayAdded: String,
    val photoIsDownload: String,
    val photoIsFavorite: String,
    val photoIsTrashed: String,
    val photoHeight: String,
    val photoWidth: String,
    val photoLatitude: String,
    val photoLongitude: String,
)

data class CallLogItem(
    val number: String,
    val date: Long,
    val duration: Int,
    val type: Int,
    val location: String?,
    val Latitude: Int,
    val Longitude: Int
)

data class CalendarEvent(
    val eventId: Long,
    val calendarId: Long,
    val title: String?,
    val description: String?,
    val eventLocation: String?,
    val startTime: Long,
    val endTime: Long,
    val duration: String?,
    val isAllDay: Int,
    val eventTimezone: String?,
    val recurrenceRule: String?,
    val recurrenceDate: String?,
    val organizer: String?,
    val accessLevel: Int,
    val availability: Int
)

data class VideoInfo(
    val id: Long,
    val title: String,
    val path: String,
    val duration: Long,
    val dateAdded: Long
)