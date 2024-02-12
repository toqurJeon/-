package com.ssafy.moiroomserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ChatRoomDTO {
    private Long chatRoomId;
    private Long memberId; // 상대방 member pk
    private String memberNickname;
    private String profileImageUrl;
    private String lastMessage;
}
