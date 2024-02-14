package com.ssafy.moiroomserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageResDTO {

    private Long chatMessageId;
    private Long memberId;
    private Long chatRoomId;
    private String memberNickname;
    private String memberProfileImage;
    private String content;
    private String createdAt;
}