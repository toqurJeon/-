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

    //회원
    NOT_EXISTS_MEMBER_ID(BAD_REQUEST, "존재하지 않는 회원 id 입니다."),
    NOT_EXISTS_MEMBER(BAD_REQUEST, "존재하지 않는 회원입니다."),
    MEMBER_ALREADY_LOGOUT_ERROR(BAD_REQUEST, "이미 로그아웃 된 회원입니다."),
    MEMBER_ALREADY_LOGIN_ERROR(CONFLICT, "이미 로그인 된 회원입니다."),
    //파일
    NOT_EXISTS_FILE(BAD_REQUEST, "존재하지 않는 파일입니다.");

    private final HttpStatus status;
    private final String message;
}