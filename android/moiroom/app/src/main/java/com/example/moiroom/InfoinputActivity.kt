package com.example.moiroom

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
import com.example.moiroom.data.MyResponse
import com.example.moiroom.data.RequestBody
import com.example.moiroom.databinding.ActivityInfoinputBinding
import kotlinx.coroutines.launch
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 레이아웃 폴더에 있는 activity_infoinput.xml을 inflate해서 바인딩
        // xml을 실제 뷰 객체로 변환하는 과정
        binding = ActivityInfoinputBinding.inflate(layoutInflater)
        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)

        // GlobalApplication 클래스에 쉽게 접근할 수 있도록 변수명 생성
        val globalApplication = application as GlobalApplication
        // GlobalApplication 클래스에 있는 Retofit 인스턴스를 활용해서
        // API 요청을 보낼 수 있는 인터페이스(ApiInterface) 구현체 생성
        val apiInterface = globalApplication.retrofit.create(ApiInterface::class.java)
        val apiInterface2 = globalApplication.retrofit2.create(ApiInterface::class.java)

        // 입력 글자 수 업데이트
        binding.textLength.text = textLength
        // 사용자 입력 자기소개 저장하기
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                userInput = charSequence.toString()
                textLength = "${charSequence?.length ?: 0}/30"
                binding.textLength.text = textLength
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        lifecycleScope.launch {
            val response = sendPostRequest()

            // response가 null이 아니면 로그에 출력
            response?.let {
                Log.d("결과", "Not null, POST 성공 - Message: ${it.message}, Status: ${it.status}")
            }
        }

        // 지역 찾기 버튼 누르기
        binding.findInput.setOnClickListener {
            // GET 요청 보내기 1
            lifecycleScope.launch {
                val posts = apiInterface.getPosts()

                // Posts를 선택하는 다이얼로그
                showItemSelectionDialog("Choose a title", posts.map { it.title }) { selectedTitle ->
                    val selectedPosts = posts.find { it.title == selectedTitle }
                    if (selectedPosts != null) {
                        // GET 요청 보내기 2
                        lifecycleScope.launch {
                            val posts2 = apiInterface.getAnother()

                            // Posts2를 선택하는 다이얼로그
                            showItemSelectionDialog("Choose a detail", posts2.map { it.title }) { selectedDetail ->
                                val selectedPosts2 = posts2.find { it.title == selectedDetail }
                                if (selectedPosts2 != null) {
                                    binding.findInput.text =
                                            "${selectedPosts.title}, ${selectedPosts2.title}"

                                    // 확인 버튼
                                    binding.saveButton.setOnClickListener {
                                        println("UserInput: ${selectedPosts.id}, ${selectedPosts2.id}, $userInput")
                                        // POST 요청 구현 필요

                                        // 액티비티 이동
                                        val intent = Intent(this@InfoinputActivity, NowMatchingActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showItemSelectionDialog(title: String, items: List<String>, onItemSelected: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setItems(items.toTypedArray()) { _, which ->
            val selectedTitle = items[which]
            onItemSelected(selectedTitle)
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private suspend fun sendPostRequest(): MyResponse? {
        val globalApplication = application as GlobalApplication

        val apiService = globalApplication.retrofit2.create(ApiInterface::class.java)

        val requestBody = RequestBody(0.1354, 0.3159, 0.7561)

        return try {
            val response = apiService.postData(requestBody)
            Log.d("결과", "POST 성공 - Message: ${response.message}, Status: ${response.status}")
            response
        } catch (e: Exception) {
            Log.e("에러", "POST 요청 보내기 오류", e)
            null
        }
    }
}
