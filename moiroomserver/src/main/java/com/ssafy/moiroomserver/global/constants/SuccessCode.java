package com.ssafy.moiroomserver.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum SuccessCode {

    //일반
    UPLOAD_IMAGE(OK, "이미지 업로드 성공"),
    //회원
    UPLOAD_PROFILE_IMAGE(OK, "프로필 사진 업로드 성공"),
    MODIFY_MEMBER_INFO(OK, "회원 정보 수정 성공"),
    LOGIN_MEMBER(CREATED, "로그인 성공"),
    LOGOUT_MEMBER(CREATED, "로그아웃 성공"),
    GET_MEMBER_BY_ID(CREATED, "회원 정보 조회 성공"),
    MODIFY_MEMBER_TOKEN(OK, "토큰 업데이트를 완료하였습니다!"),
    //지역
    GET_METROPOLITANS(OK, "광역시 정보를 성공적으로 가져왔습니다!"),
    GET_CITIES(OK, "시군구 정보를 성공적으로 가져왔습니다!");


    private final HttpStatus status;
    private final String message;
}