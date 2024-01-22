package com.ssafy.moiroomserver.chat.service.impl;

import com.ssafy.moiroomserver.chat.repository.ChatMessageRepository;
import com.ssafy.moiroomserver.chat.repository.ChatRoomRepository;
import com.ssafy.moiroomserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
}