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
    GET_MEMBER_BY_ID(OK, "회원 정보 조회 성공"),
    MODIFY_MEMBER_TOKEN(OK, "토큰 업데이트를 완료하였습니다!"),
    //지역
    GET_METROPOLITANS(OK, "광역시 정보를 성공적으로 가져왔습니다!"),
    GET_CITIES(OK, "시군구 정보를 성공적으로 가져왔습니다!"),
    //Flask
    ADD_ALL_CHARACTER_INFO(CREATED, "회원의 전체 특성 및 관심사 정보 추가 성공"),
    GET_INFO_FOR_MATCHING(OK, "매칭 계산을 위한 정보 조회 성공"),
    ADD_MATCHING_RESULT(CREATED, "매칭 결과 추가 성공"),
    //매칭
    GET_MATCHING_ROOMMATE_LIST(OK, "추천 룸메이트 리스트 조회 성공"),
    NO_MATCHING_ROOMMATE_LIST(NO_CONTENT, "조회에 성공했지만 보여줄 추천 룸메이트 리스트는 없습니다."),
    //채팅
    ADD_CHAT_ROOM(CREATED, "채팅방 생성에 성공하였습니다."),
    GET_CHAT_ROOM(OK, "채팅방 목록을 성공적으로 가져왔습니다."),
    ADD_CHAT_MESSAGE(OK, "채팅 메시지를 성공적으로 저장하였습니다."),
    GET_CHAT_MESSAGE(OK, "채팅 메시지를 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String message;
}