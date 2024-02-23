package com.example.moiroom.socket

import android.util.Log
import com.example.moiroom.data.Chat
import com.example.moiroom.data.UserResponse
import com.example.moiroom.utils.CachedUserInfoLiveData
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.activity.ChatActivity
import com.example.moiroom.adapter.ChatAdapter


//class ChatSocketManager(private val activity: ChatActivity) {
//    private lateinit var stompClient: StompClient
//
//    fun connect() {
//        val socketUrl = "wss://moiroom.n-e.kr/ws"
//        val client = OkHttpClient()
//
//        // Stomp Connection 초기화
//        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, socketUrl, null, client)
//
//        stompClient.lifecycle().subscribe { event ->
//            when (event.type) {
//                LifecycleEvent.Type.OPENED -> Log.d("MYMYTAG", "Stomp connection opened")
//                LifecycleEvent.Type.ERROR -> Log.e("MYMYTAG", "Error", event.exception)
//                LifecycleEvent.Type.CLOSED -> Log.d("MYMYTAG", "Stomp connection closed")
//                else -> Log.d("MYMYTAG", "Other event: ${event.type}")
//            }
//        }
//
//        // Stomp 연결
//        stompClient.connect()
//    }
//
//    fun send(chatRoomId: String, message: String) {
//        val destination = "/queue/chat/room/$chatRoomId"
//        stompClient.send(destination, message)
//    }
//
//    fun close() {
//        // Stomp 연결 해제
//        stompClient.disconnect()
//    }
//
//    fun subscribe(destination: String) {
//        // "/topic" 접두사 추가
//        val destinationWithPrefix = "/topic$destination"
//
//        // 메시지 수신 구독
//        stompClient.topic(destinationWithPrefix).subscribe { message ->
//            Log.d("MYMYTAG", "Received message: ${message.payload}")
//
//            // 메시지 처리를 위한 콜백 함수 실행
////            handleReceivedMessage(message.payload)
//        }
//    }
//}


class ChatSocketManager(private val activity: ChatActivity) {

    private lateinit var stompClient: StompClient
    var cachedUserInfo: UserResponse.Data.Member? =
        CachedUserInfoLiveData.cacheUserInfo.get("userInfo")
    val currentDateTime =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//    private val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)

    lateinit var adapter: ChatAdapter
    lateinit var recyclerView: RecyclerView

    fun setAdapterAndRecyclerView(adapter: ChatAdapter, recyclerView: RecyclerView) {
        this.adapter = adapter
        this.recyclerView = recyclerView
    }

    fun connect(chatRoomId: Long, memberNickname: String, memberProfileImage: String, memberId: Long) {
            val socketUrl = "wss://moiroom.n-e.kr/ws"

            stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, socketUrl)
        stompClient.topic("/queue/chat/room/$chatRoomId")
            .subscribe({ topicMessage ->
                // 메시지 수신 확인
                Log.d("Check Message", "Received Message: ${topicMessage.payload}")

                val newChat = Chat(
                    adapter.itemCount + 1,
                    memberId,
                    chatRoomId,
                    memberNickname,
                    memberProfileImage,
                    topicMessage.payload,
                    currentDateTime
                )
                // Chat 객체 생성 확인
                Log.d("Check Message", "Created Chat object: $newChat")

                activity.runOnUiThread {
                    // 화면 업데이트 전 adapter의 아이템 개수 확인
                    Log.d("Check Message", "Number of items before update: ${adapter.itemCount}")

                    adapter.addData(newChat)
                    recyclerView.scrollToPosition(adapter.itemCount - 1)

                    // 화면 업데이트 후 adapter의 아이템 개수 확인
                    Log.d("Check Message", "Number of items after update: ${adapter.itemCount}")
                }
            }, { throwable ->
                // 오류 처리
                Log.e("STOMP", "Error on websocket connection", throwable)
            })

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.i("ELSE", lifecycleEvent.message)
                }
            }
        }
        stompClient.connect()
    }

    fun subscribe(chatRoomId: Long) {
        Log.d("MYMYTAG", "Subscribe function is called with chatRoomId: $chatRoomId")
        stompClient.topic("/queue/chat/room/$chatRoomId").subscribe({ topicMessage ->
            Log.d("MYMYTAG", "Received: ${topicMessage}")
        }, { throwable ->
            Log.e("MYMYTAG", "Error on subscribe topic", throwable)
        })
    }

    fun send(chatRoomId: String, message: String) {
//            stompClient.send("/queue/chat/room/$chatRoomId", message).subscribe()
        stompClient.send("/pub/room/$chatRoomId/send",message).subscribe()
    }


    fun disconnect() {
            stompClient.disconnect()
        }

        // JSON 형식인지 확인
//        private fun isJson(message: String): Boolean {
//            return try {
//                Gson().fromJson(message, Any::class.java)
//                true
//            } catch (e: JsonSyntaxException) {
//                false
//            }
//        }


    }
