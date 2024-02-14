package com.example.moiroom


import ApiService
import ChatAdapter
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.data.Chat
import com.example.moiroom.databinding.ActivityChatBinding
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private var chatRoomId: Int = -1
    private var memberId: Long = -1

    private val apiService: ApiService by lazy {
        NetworkModule.provideRetrofit(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 채팅방 리스트 (ChattingFragment)로부터 전달된 chatRoomId 받기
        chatRoomId = intent.getIntExtra("chatRoomId", -1)
        memberId = intent.getLongExtra("memberId", -1)
        Log.d("TAG", "receivedData: $memberId")

        // chatRoomId 적용
        if (chatRoomId == -1) {
            binding.chatRoomName.text = "chatRoomName : $memberId"
        } else {
            binding.chatRoomName.text = "chatRoomName : $chatRoomId"
        }

        // 뒤로 가기 버튼
        binding.backwardButton.setOnClickListener {
            onBackPressed()
        }

        // recyclerView 적용
        recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        // 항상 가장 밑으로 이동
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        // 코루틴을 사용해서 서버에서 채팅 데이터를 가져옴
        lifecycleScope.launch {
            try {
                val data = getListOfChatData().toMutableList()
                adapter = ChatAdapter(data)
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                // 데이터를 가져오는 중 오류 발생
                Toast.makeText(this@ChatActivity, "Failed to load data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

//        binding.sendMsgBtn.setOnClickListener {
//            val message = binding.sendMsg.text.toString().trim()
//            if (message.isNotEmpty()) {
//                val newChat = Chat(
//                    data.size + 1,
//                    1,
//                    1,
//                    message,
//                    Instant.now()
//                )
//                data.add(newChat)
//                adapter.updateData(data.toList())
//                binding.sendMsg.text.clear()
//                scrollToLastItem()
//            }
//        }

        val btnShowModal = binding.exitBtn
        btnShowModal.setOnClickListener {
            showExitDialog()
        }
    }

    private fun scrollToLastItem() {
        val lastItemPosition = adapter.itemCount - 1
        if (lastItemPosition >= 0) {
            recyclerView.scrollToPosition(lastItemPosition)
        }
    }

    private suspend fun getListOfChatData(): List<Chat> {
        val response = apiService.getChatMessages(chatRoomId)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.data.content
        } else {
            throw Exception("Failed to load chat data: ${response.message()}")
        }
    }

    private fun showExitDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_exit)

        val textExitMessage: TextView = dialog.findViewById(R.id.textExitMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        btnYes.setOnClickListener {
            dialog.dismiss() // 다이얼로그 닫기
            onBackPressed()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
