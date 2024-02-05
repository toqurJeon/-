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

    private lateinit var binding: ActivityNowMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowMatchingBinding.inflate(layoutInflater)

        // SharedPreferences에서 'isButtonClicked' 값을 가져옴
        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)

        if (isButtonClicked) {

            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish()

        } else {
            setContentView(binding.root)

            binding.mainLayout.setOnClickListener {

                showAuthorityDialog()

                 // 클릭 여부를 SharedPreferences에 저장
                 val editor = sharedPreferences.edit()
                 editor.putBoolean("isButtonClicked", true) // 버튼 클릭 후 'true'로 변경
                 editor.apply()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_SETTINGS = 1001
    }

    private fun showAuthorityDialog() {

        val dialog = Dialog(this)
        val dialogBinding = DialogAuthorityBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.authorityMessage.text = "앞으로의 권한을 설정해주시면, 나에게 맞는 룸메이트를 찾을 가능성이 올라가요!"

        dialogBinding.confirmButton.setOnClickListener {
            permissionRequest()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun goSettingActivityAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("권한이 필요합니다.\n권한 -> 통화 및 저장공간 -> 허용")
            .setPositiveButton("허용하러 가기") { _, _ ->
                val goSettingPermission = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                goSettingPermission.data = Uri.parse("package:$packageName")
                startActivityForResult(goSettingPermission, REQUEST_CODE_SETTINGS)
            }
            .setNegativeButton("취소") { _, _ ->
                goInsta()
            }
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 설정 화면에서 돌아왔을 때 처리
        if (requestCode == REQUEST_CODE_SETTINGS) {
            Log.d("TAG", "Settings Activity Returned")

            goInsta()
        }
    }

    private fun goInsta() {
        Log.d("TAG", "goInsta: 인스타그램 추출 액티비티로 이동")
        val intent = Intent(this, InstagramExtract::class.java)
        startActivity(intent)
    }

    private fun permissionRequest() {

        // 통화 기록 읽기 허용 여부
        val isReadCallGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED

        // 저장소 접근 허용 여부
        val isExternalStorageGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (isReadCallGranted && isExternalStorageGranted) {
            Log.d("권한 설정", "permissionRequest: 이미 모든 권한이 허용됨")

            goInsta()

        } else if (isReadCallGranted) {
            Log.d("권한 설정", "permissionRequest: Call Permission만 허용됨")

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )

        } else if (isExternalStorageGranted) {
            Log.d("권한 설정", "permissionRequest: External Storage Permission만 허용됨")

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CALL_LOG
                ),
                PERMISSION_REQUEST_CODE
            )

        } else {
            Log.d("권한 설정", "permissionRequest: 모든 권한이 허용되지 않음")

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var neverFlag: Boolean = false

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "${permissions[i]} 권한이 허용되었습니다.")
                    // 권한이 허용된 경우
                } else {
                    Log.d("TAG", "${permissions[i]} 권한이 거부되었습니다.")

                    neverFlag = true

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                        // 사용자가 "다시 묻지 않음" 옵션을 선택하지 않고 권한을 거부한 경우

                        Log.d("TAG", "onRequestPermissionsResult: 권한이 거부되었습니다.]")
                    } else {
                        // 사용자가 "다시 묻지 않음" 옵션을 선택하고 권한을 거부한 경우

                        Log.d("TAG", "onRequestPermissionsResult: 다시 묻지 않음 설정.")
//                        Toast.makeText(this, "권한을 활성화하려면 설정 메뉴로 이동해주세요.", Toast.LENGTH_SHORT)
//                            .show()
                    }
                }
            }
            if (neverFlag) {
                // 다시 묻지않음 설정이 활성화되어있을 경우
                // 사용자에게 설정으로 이동하여 접근권한을 허용하도록 권유
                goSettingActivityAlertDialog()
            } else {
                // 허용되어 있음
                goInsta()
            }
        }
    }
}
