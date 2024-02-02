package com.example.moiroom

import android.app.Service
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.RequestBody
import com.example.moiroom.extraction.PhotoExtract
import java.io.File
import android.os.Handler
import androidx.exifinterface.media.ExifInterface
import java.io.IOException
import java.io.InputStream

class MyForegroundService : Service() {
    private val handler = Handler()
    private val interval: Long = 5000 // 5 seconds

    //        val serviceIntent = Intent(this, MyForegroundService::class.java)
//        startService(serviceIntent)
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 여기에 백그라운드에서 실행할 코드 작성
        Log.d("MyForegroundService", "Foreground service is running.")
        handler.postDelayed(object : Runnable {
            override fun run() {
                // 주기적으로 실행될 코드 작성
                val photos = getAllPhotos().reversed()
                Log.d("백그라운드", "$photos")

                // 다음 실행을 위해 다시 호출
                handler.postDelayed(this, interval)
            }
        }, interval)

        return START_STICKY
    }

    fun getAllPhotos(): ArrayList<String> {
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
                val photoDayAdded = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
                val photoIsDownload = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_DOWNLOAD))
                val photoIsFavorite = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_FAVORITE))
                val photoIsTrashed = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_TRASHED))
                val photoHeight = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT))
                val photoWidth = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH))
                val photoLongitude = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE))
                val photoLatitude = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE))
                val a = getImageLocation(this, Uri.fromFile(File(photoPath)))
                Log.d("테스트", "$a")
                photos.add("{$photoPath, $photoDayAdded, $photoIsDownload, $photoIsFavorite, $photoIsTrashed, $photoHeight, $photoWidth, $photoLongitude, $photoLatitude}")

                //                photos.add(photoPath)
                break

            }
        }

        return photos
    }

    fun getImageLocation(context: Context, imageUri: Uri): Pair<Double, Double>? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("1","1")
            try {
                val contentResolver: ContentResolver = context.contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(imageUri)

                inputStream?.use {
                    val exif = ExifInterface(inputStream)
                    val latLong = FloatArray(2)
                    Log.d("3","${exif.getLatLong(latLong)}")
                    if (exif.getLatLong(latLong)) {
                        val latitude = latLong[0].toDouble()
                        val longitude = latLong[1].toDouble()

                        return Pair(latitude, longitude)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("2","$e")
            }
        } else {

            // For versions before Android 11, use MediaStore to retrieve location info.
            val projection = arrayOf(
                MediaStore.Images.Media.LATITUDE,
                MediaStore.Images.Media.LONGITUDE
            )

            context.contentResolver.query(imageUri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE))
                    val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE))
                    return Pair(latitude, longitude)
                }
            }
        }

        return null
    }
}