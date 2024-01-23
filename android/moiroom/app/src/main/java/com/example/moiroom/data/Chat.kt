package com.example.moiroom.data

import java.time.Instant

data class Chat(
    val id: Int,
    val member_id: Int,
    val chat_room_id: Int,
    val content: String,
    val created_at: Instant
)
