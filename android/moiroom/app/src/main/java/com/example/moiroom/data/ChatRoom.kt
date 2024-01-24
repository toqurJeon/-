package com.example.moiroom.data

import java.time.Instant

data class ChatRoom(
    val id: Int,
    val last_message: String,
    val created_at: Instant
)
