package com.ssafy.moiroomserver.chat.repository;

import com.ssafy.moiroomserver.chat.entity.MemberChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {
}
