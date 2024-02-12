package com.ssafy.moiroomserver.chat.service;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;

public interface ChatService {
    void save(ChatRequest request);

    void addChatRoom(Long memberId);
}