package com.ssafy.moiroomserver.chat.service;

import com.ssafy.moiroomserver.chat.dto.ChatMessageReqDTO;
import com.ssafy.moiroomserver.global.dto.PageResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ChatService {
    void addChatMessage(ChatMessageReqDTO request, Long chatRoomId);

    void addChatRoom(HttpServletRequest request, Long memberId);

    PageResponse getChatRooms(HttpServletRequest request, int pgno);

    PageResponse getChatMessages(HttpServletRequest request, Long chatRoomId, int pgno);
}