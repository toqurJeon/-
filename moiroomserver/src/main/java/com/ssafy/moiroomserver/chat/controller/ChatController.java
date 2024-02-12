package com.ssafy.moiroomserver.chat.controller;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;
import com.ssafy.moiroomserver.chat.service.ChatService;
import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages")
    public void chat(@Valid ChatRequest request) {
        chatService.save(request);
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + request.getRoomId(),
                request.getMessage());
    }

    /**
     * 대화방 추가 api
     * 대화방 추가시 member_chat_room 테이블에도 정보가 추가 된다.
     * member_chat_room에는 상대방과 나에 대한 pk 정보를 가지고 있어야 한다.
     * @param memberId
     */
    @PostMapping("/{memberId}") // 대화 상대 member pk
    public ApiResponse<?> addChatRoom(HttpServletRequest request,
                                      @PathVariable Long memberId) {
        chatService.addChatRoom(request, memberId);
        return ApiResponse.success(SuccessCode.ADD_CHAT_ROOM);
    }
}