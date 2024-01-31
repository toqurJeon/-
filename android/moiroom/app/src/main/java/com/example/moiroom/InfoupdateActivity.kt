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

class InfoupdateActivity : AppCompatActivity() {

    // 레이아웃 바인딩
    private lateinit var binding: ActivityInfoupdateBinding

    // 사진 선택 요청 코드
    private val PICK_IMAGE_REQUEST = 1

    private var imageChanged: Boolean = false
    private var nicknameChanged: Boolean = false
    private var introductionChanged: Boolean = false
    private var locationChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            // 지역 찾기
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
            // PATCH request 보내기
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
            val memberProfileImageUrl = selectedImageUri.toString()

            imageChanged = true
            isChanged()
        }
    }

    override fun onBackPressed() {
        // 뒤로가기
        finish()
    }
}
