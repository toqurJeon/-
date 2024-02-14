package com.ssafy.moiroomserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReqDTO {

    private Long senderId; // 송신자 pk
    private String senderName; // 송신자의 이름
    private String message;
    private String createdAt;
}