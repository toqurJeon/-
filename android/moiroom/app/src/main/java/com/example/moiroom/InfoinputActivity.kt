package com.example.moiroom

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.databinding.ActivityInfoinputBinding
import kotlinx.coroutines.launch
import retrofit2.create

class InfoinputActivity : AppCompatActivity() {

    // 뷰바인딩
    private lateinit var binding: ActivityInfoinputBinding
    // 사용자가 입력한 자기소개 저장
    private var userInput: String = ""
    private var textLength: String = "0/30"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoinputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val globalApplication = application as GlobalApplication
        val apiInterface = globalApplication.retrofit.create(ApiInterface::class.java)

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
                                        val intent = Intent(this@InfoinputActivity, NaviActivity::class.java)
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
}
