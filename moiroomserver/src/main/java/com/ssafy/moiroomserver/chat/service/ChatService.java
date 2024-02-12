package com.ssafy.moiroomserver.chat.service;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;
import com.ssafy.moiroomserver.global.dto.PageResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ChatService {
    void save(ChatRequest request);

    void addChatRoom(HttpServletRequest request, Long memberId);

    PageResponse getChatRooms(HttpServletRequest request, int pgno);
}