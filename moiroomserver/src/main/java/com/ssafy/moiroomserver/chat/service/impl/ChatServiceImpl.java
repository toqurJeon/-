package com.ssafy.moiroomserver.chat.service.impl;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;
import com.ssafy.moiroomserver.chat.entity.ChatRoom;
import com.ssafy.moiroomserver.chat.entity.MemberChatRoom;
import com.ssafy.moiroomserver.chat.repository.ChatMessageRepository;
import com.ssafy.moiroomserver.chat.repository.ChatRoomRepository;
import com.ssafy.moiroomserver.chat.repository.MemberChatRoomRepository;
import com.ssafy.moiroomserver.chat.service.ChatService;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.NOT_EXISTS_MEMBER;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final KakaoService kakaoService;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void save(ChatRequest request) {

    }

    @Override
    public void addChatRoom(HttpServletRequest request, Long memberId) {
        // 채팅방 추가
        ChatRoom chatRoom = ChatRoom.builder().build();
        chatRoomRepository.save(chatRoom);

        // member_chat_room 회원 정보 추가하기 -> 내 pk와 상대방 pk를 저장해두기
        Member myMember = kakaoService.getMemberByHttpServletRequest(request);
        Member otherMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER));
        MemberChatRoom myMemberChatRoom = MemberChatRoom.builder()
                .chatRoom(chatRoom)
                .member(myMember)
                .build();

        MemberChatRoom otherMemberChatRoom = MemberChatRoom.builder()
                .chatRoom(chatRoom)
                .member(otherMember)
                .build();

        memberChatRoomRepository.save(myMemberChatRoom);
        memberChatRoomRepository.save(otherMemberChatRoom);
    }
}