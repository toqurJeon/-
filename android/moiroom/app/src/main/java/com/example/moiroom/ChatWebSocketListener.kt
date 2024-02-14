package com.example.moiroom

import android.util.Log
import android.widget.Toast
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class ChatWebSocketListener(private val activity: ChatActivity) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // 웹소켓이 열릴 때의 동작을 정의합니다.
        activity.runOnUiThread {
            Toast.makeText(activity, "Connected", Toast.LENGTH_SHORT).show()
            Log.d("Myactivity", "Connected")
        }
        Log.d("Myactivity", "Out Connected")
        webSocket.send("hello")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        // 메시지를 받았을 때의 동작을 정의합니다.
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        // 웹소켓이 닫힐 때의 동작을 정의합니다.
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        // 웹소켓 연결이나 메시지 전송 중 에러가 발생했을 때의 동작을 정의합니다.
        activity.runOnUiThread {
            Toast.makeText(activity, "WebSocket Error: ${t.message}", Toast.LENGTH_SHORT).show()
            Log.d("MyActivity", "WebSocket Error: ${t.message}")
        }
    }
}