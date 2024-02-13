package com.ssafy.moiroomserver.chat.repository;

import com.ssafy.moiroomserver.chat.dto.ChatRoomDTO;
import com.ssafy.moiroomserver.chat.entity.ChatRoom;
import com.ssafy.moiroomserver.chat.entity.MemberChatRoom;
import com.ssafy.moiroomserver.chat.entity.MemberChatRoomId;
import com.ssafy.moiroomserver.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, MemberChatRoomId> {

    @Query(
            "SELECT new com.ssafy.moiroomserver.chat.dto.ChatRoomDTO(\n" +
                    "    mcr.chatRoom.chatRoomId,\n" +
                    "    mcr.member.memberId,\n" +
                    "    mcr.member.nickname as memberNickname,\n" +
                    "    mcr.member.imageUrl as profileImageUrl,\n" +
                    "    mcr.chatRoom.lastMessage, " +
                    "    mcr.chatRoom.updatedAt\n" +
                    ")\n" +
                    "FROM MemberChatRoom mcr\n" +
                    "WHERE mcr.chatRoom.chatRoomId IN (\n" +
                    "    SELECT mcrSub.chatRoom.chatRoomId FROM MemberChatRoom mcrSub WHERE mcrSub.member.memberId = :memberId\n" +
                    ") AND mcr.member.memberId != :memberId"
    )
    Page<ChatRoomDTO> findAllMemberChatRoom(Long memberId, PageRequest pageRequest);

    Optional<MemberChatRoom> findMemberChatRoomByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    List<MemberChatRoom> findAllByMember(Member member);
}
