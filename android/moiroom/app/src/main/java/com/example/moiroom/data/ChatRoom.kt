package com.example.moiroom.data

import java.time.Instant

data class ChatRoom(
    val chatRoomId: Long,
    val memberId: Long,
    val memberNickname: String,
    val profileImageUrl: String,
    val lastMessage: String,
    val updatedAt: String
)

//data class ChatRoom(
//    val id: Int,
//    val last_message: String,
//    val created_at: Instant
//)

data class ChatRoomResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: ChatRoomData
)

data class ChatRoomData(
    val content: List<ChatRoom>,
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int,
    val pageSize: Int
)