package com.example.moiroom.activity

import ApiService
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.socket.ChatSocketManager
import com.example.moiroom.R
import com.example.moiroom.adapter.ChatAdapter
import com.example.moiroom.data.Chat
import com.example.moiroom.data.ChatMessageReqDTO
import com.example.moiroom.data.UserResponse
import com.example.moiroom.databinding.ActivityChatBinding
import com.example.moiroom.utils.CachedUserInfoLiveData
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.StompClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    private var lastPageNumber: Int? = null

    var cachedUserInfo: UserResponse.Data.Member? = CachedUserInfoLiveData.cacheUserInfo.get("userInfo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("shared_preferences", MODE_PRIVATE)
        val memberNickname = sharedPref.getString("memberNickname", "defaultNickname") ?: "defaultNickname"
        val memberProfileImage = sharedPref.getString("memberProfileImage", "defaultProfileImage") ?: "defaultProfileImage"
        val anothermemberId = sharedPref.getLong("memberId", 0L)

        Log.d("Chatting", "2 : $memberNickname")
        Log.d("Chatting", "2 : $memberProfileImage")
        Log.d("Chatting", "2 : $anothermemberId")

        val myMemberId = (cachedUserInfo?.memberId ?: -1).toLong()

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 채팅방 리스트 (ChattingFragment)로부터 전달된 chatRoomId 받기
        chatRoomId = intent.getLongExtra("chatRoomId", 0)
        memberId = intent.getLongExtra("memberId", 0)
        Log.d("MYTAG", "receivedData: $memberId")
        Log.d("MYTAG", "채팅Data: $chatRoomId")

//        adapter = com.example.moiroom.adapter.ChatAdapter(data, this@ChatActivity)
//        recyclerView.adapter = adapter

        // 채팅방이 생성되면 WebSocket에 연결하고 구독을 시작
        val chatSocketManager = ChatSocketManager(this)

//        // connect 메서드로 stompClient 초기화
//        chatSocketManager.connect(chatRoomId, memberNickname!!, memberProfileImage!!, memberId)

//        chatSocketManager.setAdapterAndRecyclerView(adapter, recyclerView)
//        chatSocketManager.connect(chatRoomId)

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

        lifecycleScope.launch {
            try {
                lastPageNumber = getLastPageNumber()
                var data = getListOfChatData(lastPageNumber ?: 1).toMutableList()

                // 화면이 충분히 채워지지 않았다면 이전 페이지의 채팅 데이터를 추가로 불러옵니다.
                while (data.size < 13 && lastPageNumber!! > 1) {
                    lastPageNumber = lastPageNumber!! - 1
                    val previousPageData = getListOfChatData(lastPageNumber!!)
                    data.addAll(0, previousPageData)
                }

                adapter = ChatAdapter(data, this@ChatActivity)
                recyclerView.adapter = adapter

                chatSocketManager.setAdapterAndRecyclerView(adapter, recyclerView)
                chatSocketManager.connect(chatRoomId, memberNickname,memberProfileImage,anothermemberId)

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        // Check if the user has scrolled to the top of the list
                        if (!recyclerView.canScrollVertically(-1) && lastPageNumber ?: 1 > 1) {
                            // Load previous page
                            lifecycleScope.launch {
                                lastPageNumber = lastPageNumber!! - 1
                                val previousPageData = getListOfChatData(lastPageNumber!!)
                                data.addAll(0, previousPageData)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                })

            } catch (e: Exception) {
                // 데이터를 가져오는 중 오류 발생
                val data = mutableListOf<Chat>()
                adapter = ChatAdapter(data, this@ChatActivity)
                recyclerView.adapter = adapter
            }
        }

        binding.sendMsgBtn.setOnClickListener {
            val message = binding.sendMsg.text.toString().trim()
            val currentDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            if (message.isNotEmpty()) {
                val chatMessageReqDTO = ChatMessageReqDTO(
                    senderId = cachedUserInfo!!.memberId,
                    senderName = cachedUserInfo!!.memberNickname,
                    message = message,
                    createdAt = currentDateTime
                )

                val gson = Gson()
                val json = gson.toJson(chatMessageReqDTO)

                // (이전 코드 생략)

                // 채팅 메시지 목록에 메시지 추가
                val newChat = Chat(
                    adapter.itemCount + 1,
                    cachedUserInfo!!.memberId,
                    chatRoomId,
                    cachedUserInfo!!.memberNickname,
                    "Your Profile Image",
                    message,
                    currentDateTime
                )
                adapter.addData(newChat)
                binding.sendMsg.text.clear()
                recyclerView.scrollToPosition(adapter.itemCount - 1)

                // WebSocket 통신을 통해 메시지 전송
                val destination = "$chatRoomId"
                chatSocketManager.connect(chatRoomId, memberNickname, memberProfileImage, memberId)
                chatSocketManager.send(destination, json)

            }
        }

        Log.d("채팅기록", "$chatRoomId")

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

    private suspend fun getListOfChatData(page: Int): List<Chat> {
        val response = apiService.getChatMessages(chatRoomId, page)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.data.content
        } else {
            return emptyList()
        }
    }

    private suspend fun getLastPageNumber(): Int {
        val response = apiService.getChatMessages(chatRoomId, 1)
        return response.body()?.data?.totalPages ?: 1
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