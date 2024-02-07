//package com.example.moiroom.extraction
//
//import android.content.ContentProvider
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.bumptech.glide.Glide
//import com.example.moiroom.R
//import com.example.moiroom.databinding.ActivityJaeeontestBinding
//import androidx.exifinterface.media.ExifInterface
//import androidx.lifecycle.lifecycleScope
//import com.example.moiroom.ApiInterface
//import com.example.moiroom.GlobalApplication
//import com.example.moiroom.data.MyResponse
//import com.example.moiroom.data.PhotoInfo
//import com.example.moiroom.data.RequestBody
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.File
//
//class AccessGallery : AppCompatActivity() {
//    private lateinit var binding: ActivityJaeeontestBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
//        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
//        setContentView(binding.root)
//        Log.d("함수 실행", "함수 실행")
//        // 갤러리에서 모든 사진 가져오기
//        val photos = getAllPhotos().reversed()
//        Log.d("사진들","$photos")
//        binding.textview.text = "$photos"
////         가져온 사진들에 대한 처리
//        for (photo in photos) {
//            // 각 사진에 대한 처리를 여기에 추가
//            Log.d("PhotoPath", "Photo Path: $photo")
//            binding.textview.text = "$photo"
//            binding.imageView.src = "$photo"
//            Glide.with(this)
//                .load(photo) // photo는 이미지 URL 또는 리소스 ID일 수 있습니다.
//                .into(binding.imageView)
//            break
//        }
//
//
//    }
//
////    private fun getAllPhotos(): ArrayList<String> {
////        suspend fun sendPostRequest(param1: Double, param2: Double, param3: Double): MyResponse? {
////
////            val globalApplication = application as GlobalApplication
////
////            val apiService = globalApplication.retrofit2.create(ApiInterface::class.java)
////
////            val requestBody = RequestBody(param1, param2, param3)
////
////            return try {
////                val response = apiService.postData(requestBody)
////                Log.d("결과", "POST 성공 - Message: ${response.message}, Status: ${response.status}")
////                response
////            } catch (e: Exception) {
////                Log.e("에러", "POST 요청 보내기 오류", e)
////                null
////            }
////        }
////
////        val projection = arrayOf(
////            MediaStore.Images.Media.DATA,
////            MediaStore.Images.Media.DISPLAY_NAME,
////            MediaStore.Images.Media.DATE_ADDED,
////            MediaStore.Images.Media.IS_DOWNLOAD,
////            MediaStore.Images.Media.IS_FAVORITE,
////            MediaStore.Images.Media.IS_TRASHED,
////            MediaStore.Images.Media.HEIGHT,
////            MediaStore.Images.Media.WIDTH,
////            MediaStore.Images.Media.LONGITUDE,
////            MediaStore.Images.Media.LATITUDE,
////        )
////
////        val photos = ArrayList<String>()
////        // MediaStore에서 이미지를 가져오는 쿼리
////        val cursor: Cursor? = contentResolver.query(
////            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
////            projection,
////            null,
////            null,
////            null
////        )
////        cursor?.use {
////            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
////            while (it.moveToNext()) {
////                val photoPath = it.getString(columnIndexData)
////                val photoDayAdded = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
////                val photoIsDownload = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_DOWNLOAD))
////                val photoIsFavorite = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_FAVORITE))
////                val photoIsTrashed = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.IS_TRASHED))
////                val photoHeight = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT))
////                val photoWidth = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH))
////                val photoLongitude = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE))
////                val photoLatitude = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE))
////
////
//////                lifecycleScope.launch {
//////                    val response = sendPostRequest(photoDayAdded.toDouble(), photoHeight.toDouble(), photoWidth.toDouble())
//////
//////                    // response가 null이 아니면 로그에 출력
//////                    response?.let {
////////                        Log.d("결과", "Not null, POST 성공 - Message: ${it.message}, Status: ${it.status}")
//////                    }
//////                }
////
////                // ExifInterface 객체 생성
//////                val exif = ExifInterface(photoPath)
////                // GPS 정보 추출
//////                val photoLatitude = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
//////                val photoLongitude = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
////
//////                val photoInfo = PhotoInfo(photoPath, photoDayAdded, photoIsDownload, photoIsFavorite, photoIsTrashed, photoHeight, photoWidth, photoLongitude, photoLatitude)
////
////
////
////                photos.add("{$photoPath, $photoDayAdded, $photoIsDownload, $photoIsFavorite, $photoIsTrashed, $photoHeight, $photoWidth, $photoLongitude, $photoLatitude}")
////                //                photos.add(photoPath)
//////                break
////
////            }
////        }
////
////        return photos
////    }
////
//////    private suspend fun sendPostRequest(): MyResponse? {
//////        val globalApplication = application as GlobalApplication
//////
//////        val apiService = globalApplication.retrofit2.create(ApiInterface::class.java)
//////
//////        val requestBody = RequestBody(0.1354, 0.3159, 0.7561)
//////
//////        return try {
//////            val response = apiService.postData(requestBody)
//////            Log.d("결과", "POST 성공 - Message: ${response.message}, Status: ${response.status}")
//////            response
//////        } catch (e: Exception) {
//////            Log.e("에러", "POST 요청 보내기 오류", e)
//////            null
//////        }
//////    }
////}
//
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
//
//
