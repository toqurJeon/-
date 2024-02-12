package com.ssafy.moiroomserver.chat.entity;

import com.ssafy.moiroomserver.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_chat_room")
@Getter
@IdClass(MemberChatRoomId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberChatRoom {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

}