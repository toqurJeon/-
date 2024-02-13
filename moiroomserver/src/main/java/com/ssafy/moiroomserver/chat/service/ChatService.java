package com.ssafy.moiroomserver.chat.service;

import com.ssafy.moiroomserver.chat.dto.ChatMessageReq;
import com.ssafy.moiroomserver.global.dto.PageResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ChatService {
    void addChatMessage(ChatMessageReq request, Long chatRoomId);

    void addChatRoom(HttpServletRequest request, Long memberId);

    PageResponse getChatRooms(HttpServletRequest request, int pgno);
}