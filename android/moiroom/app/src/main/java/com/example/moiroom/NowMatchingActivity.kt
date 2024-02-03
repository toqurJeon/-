package com.example.moiroom

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.moiroom.databinding.DialogAuthorityBinding
import com.example.moiroom.extraction.InstagramExtract
import android.app.AlertDialog
import android.util.Log
import com.example.moiroom.databinding.ActivityNowMatchingBinding
import android.Manifest
import android.net.Uri
import android.provider.Settings
import android.widget.Toast

class NowMatchingActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 103
    private val CALL_LOG_PERMISSION_REQUEST_CODE = 101
    private val STORAGE_PERMISSION_REQUEST_CODE = 102

    private lateinit var binding: ActivityNowMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowMatchingBinding.inflate(layoutInflater)

        // SharedPreferences에서 'isButtonClicked' 값을 가져옴
        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)

        if (isButtonClicked) {

//            val intent = Intent(this, NaviActivity::class.java)
//            startActivity(intent)
//            finish()

        } else {
            setContentView(binding.root)

            binding.mainLayout.setOnClickListener {

                showAuthorityDialog()

                // 클릭 여부를 SharedPreferences에 저장
                // val editor = sharedPreferences.edit()
                // editor.putBoolean("isButtonClicked", true) // 버튼 클릭 후 'true'로 변경
                // editor.apply()
            }
        }
    }
    private fun showAuthorityDialog() {

        val dialog = Dialog(this)
        val dialogBinding = DialogAuthorityBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.authorityMessage.text = "앞으로의 권한을 설정해주시면, 나에게 맞는 룸메이트를 찾을 가능성이 올라가요!"

        dialogBinding.confirmButton.setOnClickListener {
            requestCallLogPermission()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun goSettingActivityAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("앨범에 접근 하기 위한 권한이 필요합니다.\n권한 -> 저장공간 -> 허용")
            .setPositiveButton("허용하러 가기") { _, _ ->
                val goSettingPermission = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                goSettingPermission.data = Uri.parse("package:$packageName")
                startActivity(goSettingPermission)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun goInsta() {
        Log.d("TAG", "goInsta: 인스타까지 옴")
        val intent = Intent(this, InstagramExtract::class.java)
        startActivity(intent)
    }

    private fun requestCallLogPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALL_LOG),
                CALL_LOG_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_LOG_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 통화 기록 권한을 얻은 경우, 저장소 권한 요청
                    requestStoragePermission()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                        // 사용자가 "다시 묻지 않음" 옵션을 선택하지 않고 권한을 거부한 경우
                        Log.d("TAG", "onRequestPermissionsResult: Call 권한이 거부되었습니다. $permissions[0]")
                        ActivityCompat.requestPermissions(this, arrayOf(permissions[0]), PERMISSION_REQUEST_CODE)
                    } else {
                        // 사용자가 "다시 묻지 않음" 옵션을 선택하고 권한을 거부한 경우
                        // 사용자에게 설정 메뉴를 통해 권한을 활성화하도록 안내합니다.
                        Log.d("TAG", "onRequestPermissionsResult: Call 다시 묻지 않음 설정. $permissions[0]")
                        Toast.makeText(this, "Call 권한을 활성화하려면 설정 메뉴로 이동해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 저장소 권한을 얻은 경우, Instagram 추출 실행
                    goInsta()
                } else {
                    // Storage 권한을 허용하지 않은 경우, 원하는 동작 수행
                    // 여기에 처리하고자 하는 작업을 추가하십시오.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                        // 사용자가 "다시 묻지 않음" 옵션을 선택하지 않고 권한을 거부한 경우
                        Log.d("TAG", "onRequestPermissionsResult: Storage 권한이 거부되었습니다. $permissions[0]")
                        ActivityCompat.requestPermissions(this, arrayOf(permissions[0]), PERMISSION_REQUEST_CODE)
                    } else {
                        // 사용자가 "다시 묻지 않음" 옵션을 선택하고 권한을 거부한 경우
                        // 사용자에게 설정 메뉴를 통해 권한을 활성화하도록 안내합니다.
                        Log.d("TAG", "onRequestPermissionsResult: Storage 다시 묻지 않음 설정. $permissions[0]")
                        Toast.makeText(this, "Storage 권한을 활성화하려면 설정 메뉴로 이동해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
