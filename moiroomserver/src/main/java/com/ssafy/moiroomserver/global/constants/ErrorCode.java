package com.ssafy.moiroomserver.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //일반
    NOT_EXISTS_ID(BAD_REQUEST, "존재하지 않는 id 입니다."),
    NOT_EXISTS_ACCESS_TOKEN(BAD_REQUEST, "유효하지 않는 access token 입니다."),
    WRONG_VALUE(BAD_REQUEST, "잘못된 값이 전송되었습니다."),
    NOT_GET_SOCIAL_ID(INTERNAL_SERVER_ERROR, "social id를 성공적으로 가져오지 못했습니다."),

    //회원
    NOT_EXISTS_MEMBER_ID(BAD_REQUEST, "존재하지 않는 회원 id 입니다."),
    NOT_EXISTS_MEMBER(BAD_REQUEST, "존재하지 않는 회원입니다."),
    MEMBER_ALREADY_LOGOUT_ERROR(BAD_REQUEST, "이미 로그아웃 된 회원입니다."),
    MEMBER_ALREADY_LOGIN_ERROR(CONFLICT, "이미 로그인 된 회원입니다."),
    WRONG_ROOMMATE_SEARCH_STATUS_VALUE(BAD_REQUEST, "잘못된 룸메이트 구하는 여부 값이 전송되었습니다."),
    //파일
    NOT_EXISTS_FILE(BAD_REQUEST, "존재하지 않는 파일입니다."),
    //특성
    NOT_EXISTS_CHARACTERISTIC_ID(BAD_REQUEST, "회원 정보 중 특징 정보를 가져오지 못했습니다."),
    //관심사
    NOT_EXISTS_INTEREST_NAME(BAD_REQUEST, "존재하지 않는 관심사 이름입니다."),
    //지역
    NOT_EXISTS_METROPOLITAN_ID(BAD_REQUEST, "존재하지 않는 광역시/도 id 입니다."),
    NOT_EXISTS_CITY_ID(BAD_REQUEST, "존재하지 않는 시/군/구 id 입니다."),

    //채팅
    NOT_EXISTS_CHAT_ROOM_ID(BAD_REQUEST, "채팅방이 존재하지 않습니다."),
    NOT_EXISTS_CHAT_MESSAGE_CONTENT(BAD_REQUEST, "채팅 메시지 내용이 없습니다."),
    NOT_EXISTS_MEMBER_CHAT_ROOM(BAD_REQUEST, "해당 유저는 채팅방에 참여하고 있지 않습니다.");

    private final HttpStatus status;
    private final String message;
}