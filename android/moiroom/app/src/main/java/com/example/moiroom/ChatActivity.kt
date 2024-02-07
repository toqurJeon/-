package com.example.moiroom


import ChatAdapter
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.data.Chat
import com.example.moiroom.databinding.ActivityChatBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    // chatRoomId의 초기값
    private var chatRoomId: Int = -1
    private var memberId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 채팅방 리스트 (ChattingFragment)로부터 전달된 chatRoomId 받기
        chatRoomId = intent.getIntExtra("chatRoomId", -1)
        memberId = intent.getLongExtra("memberId", -1)
        Log.d("TAG!!!!!!!!!!1", "receivedData: $memberId")

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

    private fun getListOfChatData(): List<Chat> {
        return listOf(
            Chat(1, 1, 1, "안녕하세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(2, 2, 1, "안녕하세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(3, 1, 1, "감사합니다", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(4, 1, 1, "모든것이", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(5, 2, 1, "나두", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(6, 2, 1, "감사합니다", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(7, 2, 1, "정말루", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(8, 1, 1, "안녕히가세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(9, 2, 1, "잘가세요", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(10, 1, 1, "가지마", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(11, 2, 1, "띠용", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(12, 2, 1, "왜 가지마", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(13, 1, 1, "구냥", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(14, 2, 1, "음 말이 길어지면 어떻게 되는 지 테스트 중입니다. 엄청나게 말을 길게하는 사람이에요. 계속 말해 계속 끝까지 말을 합니다. 정말 많이 말을해요.", Instant.parse("2024-01-23T12:34:56Z")),
            Chat(15, 1, 1, "ㅋㅋ", Instant.parse("2024-01-23T12:34:56Z"))
        )
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