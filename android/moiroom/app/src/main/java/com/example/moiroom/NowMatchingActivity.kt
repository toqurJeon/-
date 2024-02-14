package com.example.moiroom

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
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
import com.example.moiroom.databinding.DialogBasicBinding
import com.example.moiroom.extraction.YoutubeExtract
import com.example.moiroom.utils.getMatchedMember
import com.example.moiroom.utils.getRequestResult
import com.example.moiroom.utils.getUserInfo

class NowMatchingActivity : AppCompatActivity() {


    private val PERMISSION_REQUEST_CODE = 103

    private lateinit var binding: ActivityNowMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowMatchingBinding.inflate(layoutInflater)

        // SharedPreferences에서 'isButtonClicked' 값을 가져옴
        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)
        val isRematching = sharedPreferences.getBoolean("isRematching", false)
        Log.d("MYTAG", "isButtonClicked(이전에 매칭을 진행했는지 확인): $isButtonClicked")
        Log.d("MYTAG", "isButtonClicked(매칭을 새롭게 하는지 확인): $isRematching")

        if (!isRematching) {
            Log.d("MYTAG", "다시 매칭하기가 아님")
            if (isButtonClicked) {
                val intent = Intent(this, NaviActivity::class.java)
                getUserInfo(this)
                getMatchedMember(this, 1)
                startActivity(intent)
                finish()

            } else {
                Log.d("MYTAG", "처음 매칭")
                setContentView(binding.root)

                binding.mainLayout.setOnClickListener {

                    showAuthorityDialog()

                    // 클릭 여부를 SharedPreferences에 저장
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isButtonClicked", true) // 버튼 클릭 후 'true'로 변경
                    editor.apply()
                }
            }
        } else {
            Log.d("MYTAG", "새롭게 매칭을 진행합니다.")
            setContentView(binding.root)

            showAuthorityDialog()

            val editor = sharedPreferences.edit()
            editor.putBoolean("isRematching", false)
            editor.apply()
        }
    }

    companion object {
        const val REQUEST_CODE_SETTINGS = 1001
        const val REQUEST_INSTAGRAM_PERMISSION = 1002
        var callAuth = false
        var mediaAuth = false
        var instaAuth = false
        var googleAuth = false
    }

    private fun showAuthorityDialog() {

        val dialog = Dialog(this, R.style.DialogTheme)
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
        val dialog = Dialog(this, R.style.DialogTheme)
        val dialogBinding = DialogBasicBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.dialogTitle.text = "권한 승인을 해주세요."
        dialogBinding.dialogContent.text = "권한 -> 통화 및 저장공간 -> 허용"

        dialogBinding.dialogAcceptButton.setOnClickListener {
            val goSettingPermission = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            goSettingPermission.data = Uri.parse("package:$packageName")
            startActivityForResult(goSettingPermission, REQUEST_CODE_SETTINGS)
            dialog.dismiss()
        }
        dialogBinding.dialogDenyButton.setOnClickListener {
            instagramPermissionDialog()
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 설정 화면에서 돌아왔을 때 처리
        if (requestCode == REQUEST_CODE_SETTINGS) {
            Log.d("MYTAG", "Settings Activity Returned")

            instagramPermissionDialog()
        } else if (requestCode == REQUEST_INSTAGRAM_PERMISSION) {
            Log.d("TAG", "인스타그램 권한 설정에서 돌아옴.")
            youtubePermissionDialog()
        }
    }

    private fun goInsta() {
        Log.d("TAG", "goInsta: 인스타그램 추출 액티비티로 이동")
        val intent = Intent(this, InstagramExtract::class.java)
        startActivityForResult(intent, REQUEST_INSTAGRAM_PERMISSION)
    }

    private fun instagramPermissionDialog() {
        val dialog = Dialog(this, R.style.DialogTheme)
        val dialogBinding = DialogBasicBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.dialogTitle.text = "인스타그램 권한 승인이 필요해요."
        dialogBinding.dialogContent.text = "인스타그램으로 이동할까요?"

        dialogBinding.dialogAcceptButton.setOnClickListener {
            goInsta()
            dialog.dismiss()
        }
        dialogBinding.dialogDenyButton.setOnClickListener {
            Log.d("TAG", "instagramPermissionDialog: 인스타그램 거절, 유튜브 다이얼로그 띄움")
            // 인스타그램 이동을 거절했을때의 요청 보내기

            youtubePermissionDialog()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun youtubePermissionDialog() {
        val dialog = Dialog(this, R.style.DialogTheme)
        val dialogBinding = DialogBasicBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.dialogTitle.text = "유튜브 권한 승인이 필요해요."
        dialogBinding.dialogContent.text = "유튜브로 이동할까요?"

        dialogBinding.dialogAcceptButton.setOnClickListener {
            val intent = Intent(this, YoutubeExtract::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        dialogBinding.dialogDenyButton.setOnClickListener {
            Log.d("TAG", "instagramPermissionDialog: 유튜브 거절, 매칭 중 액티비티로 이동")
            // 유튜브 이동을 거절했을때의 요청 보내기

            // 더미 응답
            getRequestResult(true, this)
            // 매칭중 액티비티로 이동
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun permissionRequest() {
        // 권한을 받아야 할 권한들의 리스트
        val permissionsToRequest = mutableListOf<String>()

        // 통화 기록 권한 허용 여부
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_CALL_LOG)
        }

        // 미디어 접근 권한 허용 여부
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // 위치 권한 허용 여부
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // 권한을 받아야하는 경우
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            instagramPermissionDialog()
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
                    if (permissions[i] == "android.permission.READ_CALL_LOG") {
                        Log.d("오스 바꾸기", "전화")

                    }
                    if (permissions[i] == "android.permission.READ_EXTERNAL_STORAGE") {
                        Log.d("오스 바꾸기", "사진")
                        mediaAuth = true
                    }
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
                instagramPermissionDialog()
            }
        }
    }
}
