package com.ssafy.moiroomserver.chat.entity;

import com.ssafy.moiroomserver.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberChatRoom implements Persistable<MemberChatRoomId> {

    @EmbeddedId
    private MemberChatRoomId memberChatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Override
    public MemberChatRoomId getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }

}