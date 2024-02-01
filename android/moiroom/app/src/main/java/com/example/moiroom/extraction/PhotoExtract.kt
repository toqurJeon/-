package com.example.moiroom.extraction

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.moiroom.ApiInterface
import com.example.moiroom.GlobalApplication
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.RequestBody
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import java.io.File
import java.io.InputStream
import androidx.exifinterface.media.ExifInterface
import com.example.moiroom.MyForegroundService
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.GpsDirectory
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import com.google.gson.Gson
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.httpPost

class PhotoExtract: AppCompatActivity() {
    private lateinit var binding: ActivityJaeeontestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val serviceIntent = Intent(this, MyForegroundService::class.java)
//        startService(serviceIntent)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
//        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        Log.d("함수 실행", "함수 실행")
        // 갤러리에서 모든 사진 가져오기
        val photos = getAllPhotos()
//            .reversed().subList(1,4)
//        Log.d("사진들", "$photos")
//        println("$photos")
        val jsonObject2 = JsonObject()
        jsonObject2.addProperty("data", "${photos}")
        Log.d("사진들", "$jsonObject2")
        postFuel(jsonObject2)
        binding.textview.text = "$photos"
        // 가져온 사진들에 대한 처리
//        for (photo in photos) {
//            // 각 사진에 대한 처리를 여기에 추가
//            Log.d("PhotoPath", "Photo Path: $photo")
//            binding.textview.text = "$photo"
////            binding.imageView.src = "$photo"
////            Glide.with(this)
////                .load(photo) // photo는 이미지 URL 또는 리소스 ID일 수 있습니다.
////                .into(binding.imageView)
//            break
//        }


    }

    fun getAllPhotos(): MutableList<Map<String, Double>> {
        suspend fun sendPostRequest(param1: Double, param2: Double, param3: Double): MyResponse? {

            val globalApplication = application as GlobalApplication

            val apiService = globalApplication.retrofit2.create(ApiInterface::class.java)

            val requestBody = RequestBody(param1, param2, param3)

            return try {
                val response = apiService.postData(requestBody)
                Log.d("결과", "POST 성공 - Message: ${response.message}, Status: ${response.status}")
                response
            } catch (e: Exception) {
                Log.e("에러", "POST 요청 보내기 오류", e)
                null
            }
        }

        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.IS_DOWNLOAD,
            MediaStore.Images.Media.IS_FAVORITE,
            MediaStore.Images.Media.IS_TRASHED,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media.LATITUDE,
        )

        val photos = ArrayList<String>()
        val location = mutableListOf<Map<String, Double>>()
        // MediaStore에서 이미지를 가져오는 쿼리
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use {
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val photoPath = it.getString(columnIndexData)
                val photoDayAdded =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
                val photoIsDownload =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_DOWNLOAD))
                val photoIsFavorite =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_FAVORITE))
                val photoIsTrashed =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_TRASHED))
                val photoHeight =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT))
                val photoWidth =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH))
                val photoLongitude =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE))
                val photoLatitude =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE))
//                val a = getImageLocation(this, Uri.fromFile(File(photoPath)))
                val a = readMetadata(photoPath)
//                Log.d("테스트", "$a")
                if (a != null) {
                    location.add(a)
                    break
//                    photos.add("[$photoPath, $photoDayAdded, $photoIsDownload, $photoIsFavorite, $photoIsTrashed, $photoHeight, $photoWidth, $photoLongitude, $a]")
                }
                //                photos.add(photoPath)
//                break

            }
//            Log.d("위치","$location")
        }

        return location
    }


    private fun readMetadata(path: String): MutableMap<String, Double>? {
        val fileScope = path
        val pdsLat: String
        val pdsLon: String

        val file = File(fileScope)

        try {
            val metadata = ImageMetadataReader.readMetadata(file)
            val gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory::class.java)
            // 위도, 경도 호출
            if (gpsDirectory != null &&
                gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE) &&
                gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE)
//                gpsDirectory.containsTag(GpsDirectory.TAG_ALTITUDE)
            ) {
                pdsLat = gpsDirectory.getGeoLocation().latitude.toString()
                pdsLon = gpsDirectory.getGeoLocation().longitude.toString()
                val lat = pdsLat.toDouble() // 위도
                val lon = pdsLon.toDouble() // 경도
                val jsonObject = JsonObject()
//                val myMap = mutableMapOf<String, Double>()
                val jsonData = mutableMapOf(
                    "latitude" to lat,
                    "longitude" to lon
                )
                jsonObject.addProperty("latitude", lat)
                jsonObject.addProperty("longitude", lon)
//                myMap["latitude"] = lat
//                myMap["longitude"] = lon
                return jsonData

            } else {
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    private fun postFuel(data: JsonObject) {
        // FuelManager 설정 (선택사항)
        FuelManager.instance.basePath = "http://i10a308.p.ssafy.io:5000"
        Log.d("최종 전송 데이터", "$data")
        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/count_clusters")
                    .header("Content-Type" to "application/json")
                    .body(
                        """
                        {
                            "data": ${Gson().toJson(data)}
                        }
                        """.trimIndent()
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
