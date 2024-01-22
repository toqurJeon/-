package com.example.moiroom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.databinding.ActivityInfoinputBinding
import kotlinx.coroutines.launch
import retrofit2.create


class InfoinputActivity : AppCompatActivity() {

    // xml 레이아웃과 바인딩
    private lateinit var binding: ActivityInfoinputBinding
    // 사용자 입력 문구 (자기소개)
    private var userInput: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoinputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrofit 가져오기
        val globalApplication = application as GlobalApplication
        val apiInterface = globalApplication.retrofit.create(ApiInterface::class.java)

        // 사용자 입력 바인딩
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                userInput = charSequence.toString()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        // GET 요청 보내기 1
        lifecycleScope.launch {
            val posts = apiInterface.getPosts()

            val spinnerAdapter = ArrayAdapter(
                this@InfoinputActivity,
                android.R.layout.simple_spinner_item,
                posts.map { it.title }
            )
            binding.spinnerTitles.adapter = spinnerAdapter

            binding.spinnerTitles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
//                    binding.textSelectedTitle.text = "선택된 지역 : ${posts[position].id}"
                    val selectedTitle = posts[position].id

                    // GET 요청 보내기 2
                    lifecycleScope.launch {
                        val posts2 = apiInterface.getAnother()

                        val spinnerAdapter2 = ArrayAdapter(
                            this@InfoinputActivity,
                            android.R.layout.simple_spinner_item,
                            posts2.map { it.title }
                        )
                        binding.spinnerDetails.adapter = spinnerAdapter2
                        binding.spinnerDetails.visibility = View.VISIBLE

                        binding.spinnerDetails.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentDetails: AdapterView<*>?,
                                viewDetails: View?,
                                positionDetails: Int,
                                idDetails: Long
                            ) {
                                val selectedDetail = posts2[positionDetails].id

                                binding.textSelectedTitle.text = "선택된 지역 : ${posts[position].id}, ${posts2[positionDetails].id}"

                                // 확인 버튼
                                binding.saveButton.setOnClickListener {
                                    println("UserInput: ${posts[position].id}, ${posts2[positionDetails].id}, $userInput")
                                    // POST 요청 구현 필요

                                    // 액티비티 이동
                                    val intent = Intent(this@InfoinputActivity, NaviActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}