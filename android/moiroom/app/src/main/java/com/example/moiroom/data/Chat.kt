package com.example.moiroom.data

import java.time.Instant

data class Chat(
    val chatMessageId: Int,
    val memberId: Int,
    val chatRoomId: Long,
    val memberNickname: String,
    val memberProfileImage: String,
    val content: String,
    val createdAt: String
)
data class ChatResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: ChatData
)

data class ChatData(
    val content: List<Chat>,
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int,
    val pageSize: Int
)

data class  ChatCreate(
    val code: Int,
    val status: String,
    val message: String,
)
