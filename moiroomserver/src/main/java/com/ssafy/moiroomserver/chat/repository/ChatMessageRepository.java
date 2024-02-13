package com.ssafy.moiroomserver.chat.repository;

import com.ssafy.moiroomserver.chat.dto.ChatMessageDTO;
import com.ssafy.moiroomserver.chat.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


    @Query("select new com.ssafy.moiroomserver.chat.dto.ChatMessageDTO(" +
            "cm.chatMessageId, " +
            "mcr.member.memberId, " +
            "mcr.chatRoom.chatRoomId, " +
            "mcr.member.imageUrl, " +
            "cm.content, " +
            "cm.createdAt) from ChatMessage cm " +
            "join MemberChatRoom mcr on cm.memberChatRoom.chatRoom.chatRoomId = mcr.chatRoom.chatRoomId " +
            "and cm.memberChatRoom.member.memberId = mcr.member.memberId " +
            "where cm.memberChatRoom.chatRoom.chatRoomId = :chatRoomId order by cm.createdAt asc")
    Page<ChatMessageDTO> findAllChatRoomMessage(@Param("chatRoomId") Long chatRoomId, PageRequest pageRequest);
}