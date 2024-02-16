package com.example.moiroom.extraction

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.GpsDirectory
import com.example.moiroom.data.VideoInfo
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import java.io.File
import java.io.IOException

class VideoExtract: AppCompatActivity() {
    private lateinit var binding: ActivityJaeeontestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
//        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        val videos = getVideoList(this)
        binding.textview.text = "$videos"

    }

    fun getVideoList(context: Context): List<VideoInfo> {
        val videoList = mutableListOf<VideoInfo>()

        // 비디오 쿼리를 위한 프로젝션
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.LATITUDE,
            MediaStore.Video.Media.LONGITUDE
        )

        // 비디오 쿼리
        val contentResolver: ContentResolver = context.contentResolver
        val videoUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(videoUri, projection, null, null, null)

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val dateAddedColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
//            val LatitudeColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.LONGITUDE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
//                val title = it.getString(titleColumn)
                val path = it.getString(pathColumn)
                val duration = it.getLong(durationColumn)
                val dateAdded = it.getLong(dateAddedColumn)
                val title = readMetadata(path)

//                val title2 = it.getString(LatitudeColumn)
//                Log.d("경도","$title2")
                val videoInfo = VideoInfo(id, title, path, duration, dateAdded)
                if ( title != "{\"latitude\": null, \"longitude\": null}, "){
                videoList.add(videoInfo)
            }}
        }

        return videoList
    }

    private fun readMetadata(path: String): String {
        val fileScope = path
        val pdsLat: String
        val pdsLon: String

        val file = File(fileScope)

        val stringBuilder = StringBuilder()
        try {
            val metadata = ImageMetadataReader.readMetadata(file)
            val gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory::class.java)

            // 위도, 경도 호출
            if (gpsDirectory != null &&
                gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE) &&
                gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE)
            ) {
                pdsLat = gpsDirectory.getGeoLocation().latitude.toString()
                pdsLon = gpsDirectory.getGeoLocation().longitude.toString()
                val lat = pdsLat.toDouble() // 위도
                val lon = pdsLon.toDouble() // 경도
                stringBuilder.append("{\"latitude\": $lat, \"longitude\": $lon}, ")
                return stringBuilder.toString()
            } else {
                return stringBuilder.append("{\"latitude\": null, \"longitude\": null}, ").toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return stringBuilder.append("{\"latitude\": null, \"longitude\": null}, ").toString()
        }
    }
}