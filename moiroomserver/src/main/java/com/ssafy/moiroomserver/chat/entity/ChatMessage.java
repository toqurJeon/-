package com.ssafy.moiroomserver.chat.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatMessage extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "member_id", referencedColumnName = "member_id"),
            @JoinColumn(name = "chat_room_id", referencedColumnName = "chat_room_id")
    })
    private MemberChatRoom memberChatRoom;

    private String content;

}