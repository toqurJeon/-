package com.ssafy.moiroomserver.chat.controller;

import com.ssafy.moiroomserver.chat.dto.ChatMessageReq;
import com.ssafy.moiroomserver.chat.service.ChatService;
import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room/{chatRoomId}")
    public void addChatMessage(@Payload ChatMessageReq request,
                                         @DestinationVariable("chatRoomId") Long chatRoomId) {
        chatService.addChatMessage(request);
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

    /**
     * 대화방 리스트 조회 api
     * @param request
     * @param pgno
     * @return
     */
    @GetMapping
    public ApiResponse<?> getChatRooms(HttpServletRequest request,
                                       @RequestParam(required = false, defaultValue = "1") int pgno) {
        return ApiResponse.success(SuccessCode.GET_CHAT_ROOM, chatService.getChatRooms(request, pgno));
    }
}