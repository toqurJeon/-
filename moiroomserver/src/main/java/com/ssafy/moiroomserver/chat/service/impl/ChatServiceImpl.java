package com.ssafy.moiroomserver.chat.service.impl;

import com.ssafy.moiroomserver.chat.dto.ChatRequest;
import com.ssafy.moiroomserver.chat.dto.ChatRoomDTO;
import com.ssafy.moiroomserver.chat.entity.ChatRoom;
import com.ssafy.moiroomserver.chat.entity.MemberChatRoom;
import com.ssafy.moiroomserver.chat.repository.ChatMessageRepository;
import com.ssafy.moiroomserver.chat.repository.ChatRoomRepository;
import com.ssafy.moiroomserver.chat.repository.MemberChatRoomRepository;
import com.ssafy.moiroomserver.chat.service.ChatService;
import com.ssafy.moiroomserver.global.dto.PageResponse;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.NOT_EXISTS_MEMBER;
import static com.ssafy.moiroomserver.global.constants.PageSize.GET_CHAT_ROOM_LIST_SIZE;

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
        System.out.println("myMember.getMemberId() = " + myMember.getMemberId());
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

    @Override
    public PageResponse getChatRooms(HttpServletRequest request, int pgno) {
        Member member = kakaoService.getMemberByHttpServletRequest(request);
        PageRequest pageRequest = PageRequest.of(pgno - 1, GET_CHAT_ROOM_LIST_SIZE);

        if (!memberRepository.existsById(member.getMemberId())) {
            throw new NoExistException(NOT_EXISTS_MEMBER);
        }

        // 채팅방 정보 리스트 가져오기
        Page<ChatRoomDTO> chatRoomsDTOPage = memberChatRoomRepository.findAllMemberChatRoom(member.getMemberId(), pageRequest);

        if (chatRoomsDTOPage.getTotalElements() < 1) {
            return null;
        }

        return PageResponse.builder()
                .content(chatRoomsDTOPage.getContent())
                .totalPages(chatRoomsDTOPage.getTotalPages())
                .totalElements(chatRoomsDTOPage.getTotalElements())
                .currentPage(chatRoomsDTOPage.getNumber())
                .pageSize(chatRoomsDTOPage.getSize())
                .build();
    }
}