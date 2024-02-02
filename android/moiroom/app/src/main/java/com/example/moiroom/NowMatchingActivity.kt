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
import android.Manifest
import android.util.Log

class NowMatchingActivity : AppCompatActivity() {

    private val CALL_LOG_PERMISSION_REQUEST_CODE = 101
    private val STORAGE_PERMISSION_REQUEST_CODE = 102

    override fun onResume() {
        super.onResume()

        requestStoragePermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SharedPreferences에서 'isButtonClicked' 값을 가져옴
        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked", false)

        if (isButtonClicked==false) {
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_now_matching)

            val relativeLayout: RelativeLayout = findViewById(R.id.relativeLayout)

            val onClickListener = View.OnClickListener {

                // 클릭 여부를 SharedPreferences에 저장
                val editor = sharedPreferences.edit()
                editor.putBoolean("isButtonClicked", true) // 버튼 클릭 후 'true'로 변경
                editor.apply()

                requestStoragePermission()
            }

            relativeLayout.setOnClickListener(onClickListener)
        }
    }
    private fun showAuthorityDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogAuthorityBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.authorityMessage.text = "앞으로의 권한을 설정해주시면, 나에게 맞는 룸메이트를 찾을 가능성이 올라가요?"

        dialogBinding.confirmButton.setOnClickListener {
            requestStoragePermission()
            dialog.dismiss()
        }

        dialog.show()
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
        } else {
            // If permission is already granted, proceed directly to next step
            // Call Log Permission granted, proceed to storage permission
            Log.d("TAG", "이미 콜 접근권한 얻음")
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        Log.d("TAG", "requestStoragePermission: 여기까지는 오나ㅏ?")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("TAG", "requestStoragePermission: 여기까지는 오나22222")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE
            )
            Log.d("TAG", "requestStoragePermission: 여기까지는 오나333333")
        } else {
            // If permission is already granted, proceed directly to next step
            goInsta() // Storage Permission granted, proceed to Instagram extraction
            Log.d("TAG", "이미 저장소 접근권한 얻음")
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
                    requestStoragePermission()
                    // 권한을 허용하지 않은 경우, 원하는 동작 수행
                    // 여기에 처리하고자 하는 작업을 추가하십시오.
                }
            }
            STORAGE_PERMISSION_REQUEST_CODE -> {
                Log.d("TAG", "onRequestPermissionsResult: $requestCode")
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 저장소 권한을 얻은 경우, Instagram 추출 실행
                    Log.d("TAG", "onRequestPermissionsResult: 허용받아서 간다")
                    goInsta()
                } else {
                    Log.d("TAG", "onRequestPermissionsResult: 허용안받아서 간다")
                    goInsta()

                    // 권한을 허용하지 않은 경우, 원하는 동작 수행
                    // 여기에 처리하고자 하는 작업을 추가하십시오.
                }
            }
        }
    }
}
