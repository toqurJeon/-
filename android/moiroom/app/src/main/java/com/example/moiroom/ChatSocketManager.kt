package com.example.moiroom

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import okhttp3.WebSocket
import okhttp3.Request
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.util.concurrent.Flow


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
    fun connect(chatRoomId: Long) {
        val socketUrl = "wss://moiroom.n-e.kr/ws"

        stompClient= Stomp.over(Stomp.ConnectionProvider.OKHTTP, socketUrl)
        stompClient.topic("/queue/chat/room/$chatRoomId").subscribe { topicMessage ->
            if(topicMessage.payload == null){
                Log.i("message Recieve", "null")
            }else{
                Log.i("message Recieve", topicMessage.payload)
            }
        }

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
    }

