package com.example.moiroom


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.adapter.ChatAdapter
import com.example.moiroom.data.Chat
import com.example.moiroom.databinding.ActivityChatBinding
import java.time.Instant

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    // chatRoomId의 초기값
    private var chatRoomId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 채팅방 리스트 (ChattingFragment)로부터 전달된 chatRoomId 받기
        chatRoomId = intent.getIntExtra("chatRoomId", -1)

        // chatRoomId 적용
        binding.chatRoomName.text = "chatRoomName : $chatRoomId"

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

        // 데이터를 가져오는 함수
        val data = getListOfChatData().toMutableList()
        adapter = ChatAdapter(data)
        recyclerView.adapter = adapter

        binding.sendMsgBtn.setOnClickListener {
            val message = binding.sendMsg.text.toString().trim()
            if (message.isNotEmpty()) {
                val newChat = Chat(
                    data.size + 1,
                    1,
                    1,
                    message,
                    Instant.now()
                )
                data.add(newChat)
                adapter.updateData(data.toList())
                binding.sendMsg.text.clear()
                scrollToLastItem()
            }
        }
    }

    private fun scrollToLastItem() {
        val lastItemPosition = adapter.itemCount - 1
        if (lastItemPosition >= 0) {
            recyclerView.scrollToPosition(lastItemPosition)
        }
    }

    private fun getListOfChatData(): List<Chat> {
        return listOf(
            Chat(1, 1, 1, "안녕하세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(2, 2, 1, "안녕하세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(3, 1, 1, "감사합니다", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(4, 1, 1, "모든것이", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(5, 2, 1, "나두", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(6, 2, 1, "감사합니다", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(7, 1, 1, "안녕히가세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(8, 2, 1, "잘가세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(9, 1, 1, "가지마", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(10, 2, 1, "띠용", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(11, 2, 1, "왜 가지마", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(12, 1, 1, "구냥", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(13, 2, 1, "ㅎㅎ", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(14, 1, 1, "ㅋㅋ", Instant.parse("2024-01-23T12:34:56Z"))
        )
    }

    override fun onBackPressed() {
        // 뒤로가기
        finish()
    }
}