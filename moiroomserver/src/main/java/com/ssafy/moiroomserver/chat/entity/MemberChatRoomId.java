package com.ssafy.moiroomserver.chat.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberChatRoomId implements Serializable {

    private Long member;

    private Long chatRoom;
}
