package com.ssafy.moiroomserver.chat.service;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface ChatService {
    void save(ChatRequest request);

    void addChatRoom(HttpServletRequest request, Long memberId);
}