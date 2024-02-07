package com.example.moiroom

import ApiService
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.data.City
import com.example.moiroom.data.MemberInfoUpdateRequest
import com.example.moiroom.data.Metropolitan
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.RequestBody
import com.example.moiroom.databinding.ActivityInfoinputBinding
import fetchUserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.create
import java.util.logging.Logger.global

class InfoinputActivity : AppCompatActivity() {

    // activity_infoinput.xml에 뷰바인딩
    private lateinit var binding: ActivityInfoinputBinding

    // 사용자가 입력한 자기소개 저장
    private var userInput: String = ""

    // 입력 글자 수에 따라 추후 수정
    private var textLength: String = "0/30"

    // ApiService 인스턴스를 저장할 변수
    private lateinit var apiService: ApiService

    private var metropolitans: List<Metropolitan> = listOf()
    private var cities: List<City> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 레이아웃 폴더에 있는 activity_infoinput.xml을 inflate해서 바인딩
        // xml을 실제 뷰 객체로 변환하는 과정
        binding = ActivityInfoinputBinding.inflate(layoutInflater)
        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)

        // ApiService 인스턴스 생성
        apiService = NetworkModule.provideRetrofit(this)
        // 입력 글자 수 업데이트
        binding.textLength.text = textLength
        // 사용자 입력 자기소개 저장하기
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                userInput = charSequence.toString()
                textLength = "${charSequence?.length ?: 0}/30"
                binding.textLength.text = textLength
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        lifecycleScope.launch {
//            val response = sendPostRequest()
//            // response가 null이 아니면 로그에 출력
//            response?.let {
//                Log.d("결과", "Not null, POST 성공 - Message: ${it.message}, Status: ${it.status}")
//            }
        }

        binding.findInput.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiService.getMetropolitan()
                Log.d("결과", "광역시 데이터 요청 결과: $response")
                if (response.isSuccessful) {
                    metropolitans = response.body()?.data ?: emptyList()  // 수정된 부분
                    Log.d("결과", "광역시 데이터: $metropolitans")
                    withContext(Dispatchers.Main) {
                        showItemSelectionDialog(
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
                                        cities = cityResponse.body()?.data ?: emptyList()  // 수정된 부분
                                        Log.d("결과", "군/구 데이터: $cities")
                                        withContext(Dispatchers.Main) {
                                            showItemSelectionDialog(
                                                "군/구 선택",
                                                cities.map { it.cityName }) { selectedCityName ->
                                                binding.findInput.text =
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

        binding.saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                val accessToken = sharedPreferences.getString("accessToken", null)
                val refreshToken = sharedPreferences.getString("refreshToken", null)

                Log.d("결과", "accessToken: $accessToken, refreshToken: $refreshToken")

                // accessToken과 refreshToken이 null인지 검사합니다.
                if (accessToken != null && refreshToken != null) {
                    // 사용자 정보를 가져옵니다.
                    val userInfo = fetchUserInfo(this@InfoinputActivity, accessToken, refreshToken)
                    Log.d("결과", "$userInfo")

                    // 사용자 정보가 null이 아니라면
                    if (userInfo != null) {
                        // metropolitanId와 cityId를 저장할 변수를 선언합니다.
                        var metropolitanId: Long = 0
                        var cityId: Long = 0

                        // 사용자가 선택한 광역시/도와 시/군/구 이름을 저장할 변수를 선언합니다.
                        val selectedLocation = binding.findInput.text.toString()

                        // selectedLocation을 쉼표로 분리하여 광역시/도 이름과 시/군/구 이름을 가져옵니다.
                        val locationParts = selectedLocation.split(", ")

                        // locationParts에서 광역시/도 이름과 시/군/구 이름을 가져옵니다.
                        val selectedMetropolitanName = locationParts[0]
                        val selectedCityName = locationParts[1]

                        // 광역시/도 리스트에서 사용자가 선택한 광역시/도를 찾습니다.
                        val selectedMetropolitan =
                            metropolitans.find { it.metropolitanName == selectedMetropolitanName }
                        metropolitanId = selectedMetropolitan?.metropolitanId?.toLong() ?: 0

                        // 시/군/구 리스트에서 사용자가 선택한 시/군/구를 찾습니다.
                        val selectedCity = cities.find { it.cityName == selectedCityName }
                        cityId = selectedCity?.cityId?.toLong() ?: 0

                        // 카카오에서 가져온 닉네임을 가져옵니다.
                        val memberNickname: String = userInfo.nickname
                        val memberIntroduction: String = binding.editText.text.toString()

                        // 요청 객체를 생성합니다.
                        val request = MemberInfoUpdateRequest(
                            metropolitanId,
                            cityId,
                            memberNickname,
                            memberIntroduction
                        )

                        // 코루틴을 사용하여 네트워크 요청을 비동기적으로 실행합니다.
                        val response = withContext(Dispatchers.IO) {
                            apiService.updateMemberInfo(request)
                        }

                        if (response.isSuccessful) {
                            // 요청이 성공했을 때의 처리
                            Toast.makeText(
                                this@InfoinputActivity,
                                "회원 정보 수정 성공",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // 요청이 실패했을 때의 처리
                            Toast.makeText(
                                this@InfoinputActivity,
                                "회원 정보 수정 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // 사용자 정보를 가져오지 못한 경우 처리
                        Toast.makeText(
                            this@InfoinputActivity,
                            "사용자 정보를 가져오지 못했습니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // accessToken 또는 refreshToken이 null인 경우 처리
                    Toast.makeText(this@InfoinputActivity, "토큰 정보를 가져오지 못했습니다", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


        private fun showItemSelectionDialog(
            title: String,
            items: List<String>,
            onItemSelected: (String) -> Unit
        ) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            builder.setItems(items.toTypedArray()) { _, which ->
                val selectedItem = items[which]
                onItemSelected(selectedItem)
            }
            builder.show()
        }

        // 테스트
//    private suspend fun sendPostRequest(): MyResponse? {
//        val globalApplication = application as GlobalApplication
//
//        val apiService = globalApplication.retrofit2.create(ApiInterface::class.java)
//
//        val requestBody = RequestBody(0.1354, 0.3159, 0.7561)
//        Log.d("정보", "$requestBody")
//        return try {
//            val response = apiService.postData(requestBody)
//            Log.d("결과", "POST 성공 - Message: ${response.message}, Status: ${response.status}")
//            response
//        } catch (e: Exception) {
//            Log.e("에러", "POST 요청 보내기 오류", e)
//            null
//        }
//    }
}

