package com.example.moiroom

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class ChatSocketManager(private val activity: ChatActivity){
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        Log.d("MYMYTAG","Connect 진입")
        val request = Request.Builder().url("wss://moiroom.n-e.kr/ws").build()
        Log.d("MYMYTAG","$request")
        val listener = ChatWebSocketListener(activity)
        Log.d("MYMYTAG","$listener")
        webSocket = client.newWebSocket(request, listener)
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Closing")
    }
}