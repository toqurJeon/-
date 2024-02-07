package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.global.constants.ErrorCode;
import com.ssafy.moiroomserver.global.exception.ExistException;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.exception.WrongValueException;
import com.ssafy.moiroomserver.member.dto.AddMemberDto;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.dto.MemberTokenDto;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.MemberService;
import com.ssafy.moiroomserver.s3.service.S3Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private static final int LOGIN = 1;
    private static final int LOGOUT = 0;

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    /**
     * 회원 정보 수정
     * @param infoModifyRequest 수정 시 입력할 데이터
     */
    @Transactional
    @Override
    public void modifyMemberInfo(MemberInfo.ModifyRequest infoModifyRequest) {
        Long memberId = 16L; //임시 회원 아이디 변수
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER_ID));
        if (!infoModifyRequest.getMemberProfileImage().isEmpty()) {
            infoModifyRequest.setProfileImageUrl(s3Service.uploadProfileImage(infoModifyRequest.getMemberProfileImage(), memberId));
        }
        if (infoModifyRequest.getRoommateSearchStatus() != 0 && infoModifyRequest.getRoommateSearchStatus() != 1) {
            throw new WrongValueException(); //추가하기
        }
        member.modify(infoModifyRequest);
    }

    /**
     * 카카오 회원 로그인 로직
     * @param dto
     */
    @Transactional
    @Override
    public void login(AddMemberDto dto) {

        // 이미 존재하고 있는 회원인데 로그인 상태인 경우
        if (memberRepository.existsMemberByProviderAndSocialId(dto.getProvider(), dto.getSocialId()) &&
        memberRepository.findMemberBySocialIdAndProvider(dto.getSocialId(), dto.getProvider()).getLoginStatus() == LOGIN) {
            throw new ExistException(ErrorCode.MEMBER_ALREADY_LOGIN_ERROR);
        }

        // 이미 존재하고 로그아웃 상태인 경우 -> accessToken 및 refreshToken 갱신해주기
        if (memberRepository.existsMemberByProviderAndSocialId(dto.getProvider(), dto.getSocialId()) &&
                memberRepository.findMemberBySocialIdAndProvider(dto.getSocialId(), dto.getProvider()).getLoginStatus() == LOGOUT) {
            Member member = memberRepository.findMemberBySocialIdAndProvider(dto.getSocialId(), dto.getProvider());
            member.setLoginStatus(LOGIN);
            member.setAccessToken(dto.getAccessToken());
            member.setRefreshToken(dto.getRefreshToken());
            return;
        }

        Member member = new Member();
        member.setSocialId(dto.getSocialId());
        member.setProvider(dto.getProvider());
        member.setNickname(dto.getNickname());
        member.setImageUrl(dto.getImageUrl());
        member.setBirthyear(dto.getBirthyear());
        member.setBirthday(dto.getBirthday());
        member.setName(dto.getName());
        member.setGender(dto.getGender());
        member.setAccessToken(dto.getAccessToken());
        member.setRefreshToken(dto.getRefreshToken());

        memberRepository.save(member);
    }

    /**
     * 카카오 회원 토큰 정보 업데이트
     * @param memberId
     */
    @Transactional
    @Override
    public void modifyMemberToken(Long memberId, MemberTokenDto tokenDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        member.setAccessToken(tokenDto.getAccessToken());
        member.setRefreshToken(tokenDto.getRefreshToken());

        memberRepository.save(member);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER_ID));
    }

    @Transactional
    @Override
    public void logout(Long socialId, String provider) {
        // 회원이 존재하지 않는경우
        if (!memberRepository.existsMemberByProviderAndSocialId(provider,
                socialId)) {
            throw new NoExistException(NOT_EXISTS_MEMBER);
        }

        // 이미 로그아웃 된 회원인 경우
        if (memberRepository.findMemberBySocialIdAndProvider(socialId,
                provider).getLoginStatus() == LOGOUT) {
            throw new ExistException(MEMBER_ALREADY_LOGOUT_ERROR);
        }

        // 로그아웃 진행
        memberRepository.findMemberBySocialIdAndProvider(socialId, provider)
                .setLoginStatus(LOGOUT);

    }
}