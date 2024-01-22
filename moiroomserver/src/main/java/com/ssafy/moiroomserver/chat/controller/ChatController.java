package com.ssafy.moiroomserver.chat.controller;

import com.ssafy.moiroomserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
}