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
import com.example.moiroom.data.ChatMessageReqDTO
import com.example.moiroom.databinding.ActivityChatBinding
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private var chatRoomId: Long = -1
    private var memberId: Long = -1
    private lateinit var chatSocketManager: ChatSocketManager
    private lateinit var stompClient: StompClient

    private val apiService: ApiService by lazy {
        NetworkModule.provideRetrofit(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 채팅방이 생성되면 WebSocket에 연결하고 구독을 시작
        val chatSocketManager = ChatSocketManager(this)
        chatSocketManager.connect(chatRoomId)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 채팅방 리스트 (ChattingFragment)로부터 전달된 chatRoomId 받기
        chatRoomId = intent.getLongExtra("chatRoomId", -1)
        memberId = intent.getLongExtra("memberId", -1)
        Log.d("MYTAG", "receivedData: $memberId")
        Log.d("MYTAG", "채팅Data: $chatRoomId")

//        chatSocketManager.subscribe(chatRoomId)

//        // chatRoomId 적용
//        if (chatRoomId == -1) {
//            binding.chatRoomName.text = "chatRoomName : $memberId"
//        } else {
//            binding.chatRoomName.text = "chatRoomName : $chatRoomId"
//        }

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
                adapter = ChatAdapter(data, this@ChatActivity)
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                // 데이터를 가져오는 중 오류 발생
                Toast.makeText(this@ChatActivity, "Failed to load data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        binding.sendMsgBtn.setOnClickListener {
            val message = binding.sendMsg.text.toString().trim()
            val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            if (message.isNotEmpty()) {
                val chatMessageReqDTO = ChatMessageReqDTO(
                    senderId = memberId,
                    senderName = "Your Name",
                    message = message,
                    createdAt = currentDateTime
                )

                val gson = Gson()
                val json = gson.toJson(chatMessageReqDTO)

                // (이전 코드 생략)

                // 채팅 메시지 목록에 메시지 추가
                val newChat = Chat(
                    adapter.itemCount + 1,
                    memberId,
                    chatRoomId,
                    "Your Name",
                    "Your Profile Image",
                    message,
                    currentDateTime
                )
                adapter.addData(newChat)
                binding.sendMsg.text.clear()
                recyclerView.scrollToPosition(adapter.itemCount - 1)

                // WebSocket 통신을 통해 메시지 전송
                val destination = "/room/$chatRoomId/send"
                chatSocketManager.send(destination, json)
            }
        }

        Log.d("채팅기록","$chatRoomId")

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
        Log.d("채팅기록","$chatRoomId")
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

