package com.example.moiroom

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import com.bumptech.glide.Glide
import com.example.moiroom.databinding.ActivityInfoupdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ApiService
import android.app.Dialog
import android.content.Context
import android.widget.AdapterView
import android.widget.Toast
import com.example.moiroom.adapter.DialogAdapter
import com.example.moiroom.data.City
import com.example.moiroom.data.Metropolitan
import com.example.moiroom.databinding.DialogFindCityBinding
import com.example.moiroom.databinding.DialogFindMetropolitanBinding
import com.example.moiroom.utils.getMatchedMember
import com.example.moiroom.utils.getUserInfo
import fetchUserInfo
import kotlinx.coroutines.async
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class InfoupdateActivity : AppCompatActivity() {

    // 레이아웃 바인딩
    private lateinit var binding: ActivityInfoupdateBinding

    // 접근 권한 설정. 통화기록, 파일 및 미디어, 인스타그램.

    // 사진 선택 요청 코드
    private val PICK_IMAGE_REQUEST = 1

    private var imageChanged: Boolean = false
    private var nicknameChanged: Boolean = false
    private var introductionChanged: Boolean = false
    private var locationChanged: Boolean = false

    // ApiService 인스턴스를 저장할 변수
    private lateinit var apiService: ApiService

    private var metropolitans: List<Metropolitan> = listOf()
    private var cities: List<City> = listOf()

    private var memberProfileImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService = NetworkModule.provideRetrofit(this)

        binding = ActivityInfoupdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memberId = intent.getLongExtra("memberId", -1)
        val memberProfileImageUrl = intent.getStringExtra("memberProfileImage")
        val memberNickname = intent.getStringExtra("memberNickname")
        val memberIntroduction = intent.getStringExtra("memberIntroduction")
        val metropolitanName = intent.getStringExtra("metropolitanName")
        val cityName = intent.getStringExtra("cityName")
        var memberRoomateSearchStatus = intent.getIntExtra("memberRoomateSearchStatus", 1)

        // 사용자 정보 불러오기
        binding.memberNickname.text = memberNickname
        binding.memberIntroduction.text = memberIntroduction
        binding.memberLocation.text = "$metropolitanName, $cityName"

        if (memberRoomateSearchStatus == 1) {
            binding.statusSwitch.isChecked = true
            binding.statusText.text = "룸메이트를 찾고 있어요"
        } else {
            binding.statusSwitch.isChecked = false
            binding.statusText.text = "룸메이트 안 찾아요"
        }

        // 프로필 이미지 설정
        Glide.with(this).load(memberProfileImageUrl).into(binding.memberProfileImage)

        // 프로필 이미지 수정
        binding.openGalleryButton.setOnClickListener {
            openGallery()
        }

        // 프로필 이미지 되돌리기
        binding.returnDefault.setOnClickListener {
            Glide.with(this).load(memberProfileImageUrl).into(binding.memberProfileImage)
            imageChanged = false
            isChanged()
        }

        // 닉네임 수정
        binding.editNickname.setOnClickListener {
            showEditNicknameDialog(memberNickname)
        }

        // 자기소개 수정
        binding.editIntroduction.setOnClickListener {
            showEditIntroductionDialog(memberIntroduction)
        }

        // 찾는 지역 수정
        binding.editLocation.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val response = apiService.getMetropolitan()
                Log.d("결과", "광역시 데이터 요청 결과: $response")

                if (response.isSuccessful) {

                    metropolitans = response.body()?.data ?: emptyList()  // 수정된 부분
                    Log.d("결과", "광역시 데이터: $metropolitans")
                    withContext(Dispatchers.Main) {

                        showMetropolitanSelectDialog(
                            "광역시 선택",
                            metropolitans.map { it.metropolitanName }) { selectedMetropolitanName ->
                            val selectedMetropolitan =
                                metropolitans.find { it.metropolitanName == selectedMetropolitanName }
                            if (selectedMetropolitan != null) {
                                CoroutineScope(Dispatchers.IO).launch {

                                    val cityResponse =
                                        apiService.getCities(selectedMetropolitan.metropolitanId)
                                    Log.d("결과", "군/구 데이터 요청 결과: $cityResponse")
                                    if (cityResponse.isSuccessful) {
                                        cities = cityResponse.body()?.data?.sortedBy { it.cityName } ?: emptyList()  // 수정된 부분
                                        Log.d("결과", "군/구 데이터: $cities")
                                        withContext(Dispatchers.Main) {

                                            showCitySelectDialog(
                                                "군/구 선택",
                                                cities.map { it.cityName }) { selectedCityName ->
                                                binding.memberLocation.text =
                                                    "$selectedMetropolitanName, $selectedCityName"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 룸메이트 찾는 중 상태 수정
        binding.editStatus.setOnClickListener {
            binding.statusSwitch.isChecked = !binding.statusSwitch.isChecked
        }

        binding.statusSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.statusText.text = "룸메이트 찾고 있어요"
                memberRoomateSearchStatus = 1
            } else {
                binding.statusText.text = "룸메이트 안 찾아요"
                memberRoomateSearchStatus = 0
            }
            // PATCH request
        }

        // PATCH request
        binding.patchAllButton.setOnClickListener {
            sendUpdatedInfo()
        }

        // 뒤로가기
        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun isChanged() {
        if (imageChanged) {
            binding.returnDefault.visibility = View.VISIBLE
        } else {
            binding.returnDefault.visibility = View.GONE
        }
        if (nicknameChanged) {
            binding.nicknameChanged.visibility = View.VISIBLE
        } else {
            binding.nicknameChanged.visibility = View.INVISIBLE
        }
        if (introductionChanged) {
            binding.introductionChanged.visibility = View.VISIBLE
        } else {
            binding.introductionChanged.visibility = View.INVISIBLE
        }
    }

    private fun showEditNicknameDialog(previousValue: String?) {
        val editText = EditText(this)
        editText.setText(binding.memberNickname.text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("닉네임 수정")
            .setView(editText)
            .setPositiveButton("OK") { dialog, _ ->
                val newNickname = editText.text.toString()
                if (newNickname == previousValue) {
                    binding.memberNickname.text = previousValue
                    nicknameChanged = false
                    isChanged()
                } else {
                    binding.memberNickname.text = newNickname
                    nicknameChanged = true
                    isChanged()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showEditIntroductionDialog(previousValue: String?) {
        val editText = EditText(this)
        editText.setText(binding.memberIntroduction.text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("자기소개 수정")
            .setMessage("간단한 자기소개를 해주세요.")
            .setView(editText)
            .setPositiveButton("OK") { dialog, _ ->
                val newIntroduction = editText.text.toString()
                if (newIntroduction == previousValue) {
                    binding.memberIntroduction.text = previousValue
                    introductionChanged = false
                    isChanged()
                } else {
                    binding.memberIntroduction.text = newIntroduction
                    introductionChanged = true
                    isChanged()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    // 갤러리 열기 함수
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    // 갤러리 선택 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // 선택한 이미지의 Uri를 가져옴
            val selectedImageUri: Uri? = data.data
            // 선택한 이미지를 Glide를 사용하여 이미지뷰에 표시
            if (selectedImageUri != null) {
                Glide.with(this).load(selectedImageUri).into(binding.memberProfileImage)
            }
            // 선택한 이미지의 URI를 memberProfileImageUrl에 저장
            memberProfileImageUrl = selectedImageUri.toString()

            imageChanged = true
            isChanged()
        }
    }


    private fun showMetropolitanSelectDialog(
        title: String,
        items: List<String>,
        onItemSelected: (String) -> Unit
    ) {
        val dialog = Dialog(this, R.style.DialogTheme)
        val dialogBinding = DialogFindMetropolitanBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val adapter = DialogAdapter(this, items)
        dialogBinding.dataGrid.adapter = adapter

        dialogBinding.dataGrid.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            onItemSelected(selectedItem)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showCitySelectDialog(
        title: String,
        items: List<String>,
        onItemSelected: (String) -> Unit
    ) {
        val dialog = Dialog(this, R.style.DialogTheme)
        val dialogBinding = DialogFindCityBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val adapter = DialogAdapter(this, items)
        dialogBinding.dataGrid.adapter = adapter

        dialogBinding.dataGrid.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            onItemSelected(selectedItem)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun sendUpdatedInfo() {
        val selectedLocation = binding.memberLocation.text.toString().split(", ")
        val selectedMetropolitanId = metropolitans.find { it.metropolitanName == selectedLocation[0] }?.metropolitanId ?: -1
        val selectedCityId = cities.find { it.cityName == selectedLocation[1] }?.cityId ?: -1
        val memberNickname = binding.memberNickname.text.toString()
        val memberIntroduction = binding.memberIntroduction.text.toString()
        val roommateSearchStatus = if (binding.statusSwitch.isChecked) 1 else 0

        val metropolitanIdPart = selectedMetropolitanId.toString().toRequestBody(MultipartBody.FORM)
        val cityIdPart = selectedCityId.toString().toRequestBody(MultipartBody.FORM)
        val memberNicknamePart = memberNickname.toRequestBody(MultipartBody.FORM)
        val memberIntroductionPart = memberIntroduction.toRequestBody(MultipartBody.FORM)
        val roommateSearchStatusPart = roommateSearchStatus.toString().toRequestBody(MultipartBody.FORM)

        CoroutineScope(Dispatchers.IO).launch {
            val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val accessToken = sharedPreferences.getString("accessToken", null)
            val refreshToken = sharedPreferences.getString("refreshToken", null)

            // accessToken과 refreshToken이 null인지 검사합니다.
            if (accessToken != null && refreshToken != null) {
                // 사용자 정보를 가져옵니다.
                val userInfo = fetchUserInfo(this@InfoupdateActivity, accessToken, refreshToken)

                // 사용자 정보가 null이 아니라면
                if (userInfo != null) {

                    val imageUrl: String = userInfo?.imageUrl ?: run {
                        Log.e("UpdateMemberInfo", "Image URL is null")
                        return@launch
                    }
                    // 변경된 이미지 파일이 있다면 그 파일을 MultipartBody.Part로 만듭니다.
                    val memberProfileImagePart: MultipartBody.Part = withContext(Dispatchers.IO) {
                        // 이미지 파일을 다운로드합니다.
                        val inputStream = URL(imageUrl).openStream()
                        val downloadedFile = File.createTempFile("downloaded_image", ".png")
                        FileOutputStream(downloadedFile).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }

                        // 다운로드한 파일을 MultipartBody.Part로 만듭니다.
                        val requestFile = downloadedFile
                            .asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData(
                            "memberProfileImage",
                            downloadedFile.name,
                            requestFile
                        )
                    }

                    val response = apiService.updateMemberInfo(
                        metropolitanIdPart,
                        cityIdPart,
                        memberNicknamePart,
                        memberIntroductionPart,
                        roommateSearchStatusPart,
                        memberProfileImagePart
                    )

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // 회원 정보 수정 성공
                            Toast.makeText(
                                this@InfoupdateActivity,
                                "회원 정보 수정 성공",
                                Toast.LENGTH_SHORT
                            ).show()
                            intent = Intent(this@InfoupdateActivity, NaviActivity::class.java)
                            getUserInfo()
                            getMatchedMember(this@InfoupdateActivity, 1)
                            finish()
                        } else {
                            // 회원 정보 수정 실패
                            val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                            Toast.makeText(
                                this@InfoupdateActivity,
                                "회원 정보 수정 실패: $errorMsg",
                                Toast.LENGTH_SHORT
                            ).show()
                            // 로그에 에러 메시지 출력
                            Log.e("UpdateMemberInfo", "Failed to update member info: $errorMsg")
                        }
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
