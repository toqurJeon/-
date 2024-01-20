package com.example.moiroom

import android.content.ContentProvider
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class AccessGallery : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 갤러리에서 모든 사진 가져오기
        val photos = getAllPhotos()

        // 가져온 사진들에 대한 처리
        for (photo in photos) {
            // 각 사진에 대한 처리를 여기에 추가
            Log.d("PhotoPath", "Photo Path: $photo")
        }
    }

    private fun getAllPhotos(): ArrayList<String> {
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME
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
                photos.add(photoPath)
            }
        }

        return photos
    }
}

//class MyContentProvider : ContentProvider() {
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
//        return 0
//    }
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        return null
//    }
//
//    override fun onCreate(): Boolean {
//        return false
//    }
//
//    override fun query(
//        uri: Uri,
//        projection: Array<String>?,
//        selection: String?,
//        selectionArgs: Array<String>?,
//        sortOrder: String?
//    ): Cursor? {
//        return null
//    }
//
//    override fun update(
//        uri: Uri,
//        values: ContentValues?,
//        selection: String?,
//        selectionArgs: Array<String>?
//    ): Int {
//        return 0
//    }
//}


