package com.ssafy.moiroomserver.chat.entity;

import com.ssafy.moiroomserver.member.entity.Member;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class MemberChatRoomId implements Serializable {

    private Member member;

    private ChatRoom chatRoom;
}
