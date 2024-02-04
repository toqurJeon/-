package com.ssafy.moiroomserver.chat.controller;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;
import com.ssafy.moiroomserver.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages")
    public void chat(@Valid ChatRequest request) {
        chatService.save(request);
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + request.getRoomId(),
                request.getMessage());
    }
}