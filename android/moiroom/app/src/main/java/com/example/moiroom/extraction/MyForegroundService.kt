package com.example.moiroom.extraction

import android.app.Service
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.os.Handler
import androidx.exifinterface.media.ExifInterface
import java.io.IOException
import java.io.InputStream

class MyForegroundService : Service() {
    private val handler = Handler()
    private val interval: Long = 5000 // 5 seconds

    override fun onBind(intent: Intent?): IBinder? {
        return null
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