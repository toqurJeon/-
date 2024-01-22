package com.example.moiroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.databinding.ActivityInfoinputBinding
import kotlinx.coroutines.launch
import retrofit2.create

class InfoinputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoinputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoinputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val globalApplication = application as GlobalApplication
        val apiInterface = globalApplication.retrofit.create(ApiInterface::class.java)

        lifecycleScope.launch {
            val post = apiInterface.getPosts()

            val spinnerAdapter = ArrayAdapter(
                this@InfoinputActivity,
                android.R.layout.simple_spinner_item,
                post.map { it.title }
            )
            binding.spinnerTitles.adapter = spinnerAdapter

            binding.spinnerTitles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.textSelectedTitle.text = post[position].title
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }
}