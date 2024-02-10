package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.global.constants.ErrorCode;
import com.ssafy.moiroomserver.global.exception.ExistException;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.exception.WrongValueException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.member.dto.*;
import com.ssafy.moiroomserver.member.entity.Interest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberInterestRepository;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.MemberService;
import com.ssafy.moiroomserver.s3.service.S3Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberInterestRepository memberInterestRepository;

    private static final int LOGIN = 1;
    private static final int LOGOUT = 0;

    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final KakaoService kakaoService;

    /**
     * 회원 정보 수정
     * @param memberInfoModifyReq 수정 시 입력할 데이터
     */
    @Transactional
    @Override
    public void modifyMemberInfo(HttpServletRequest request, MemberInfo.ModifyRequest memberInfoModifyReq) {
//        Long socialId = kakaoService.getInformation(request.getHeader("Authorization").substring(7));
        Member member = memberRepository.findMemberBySocialIdAndProvider(3296727084L, "kakao");
        if (member == null) {
            throw new NoExistException(NOT_EXISTS_MEMBER);
        }
        if (memberInfoModifyReq.getMemberProfileImage() != null) {
            memberInfoModifyReq.setProfileImageUrl(s3Service.uploadProfileImage(memberInfoModifyReq.getMemberProfileImage(), member));
        }
        if (memberInfoModifyReq.getRoommateSearchStatus() != null && memberInfoModifyReq.getRoommateSearchStatus() != 0 && memberInfoModifyReq.getRoommateSearchStatus() != 1) {
            throw new WrongValueException(WRONG_ROOMMATE_SEARCH_STATUS_VALUE);
        }
        member.modifyMemberInfo(memberInfoModifyReq);
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

    @Override
    public MemberInfoDetail getMemberInfoDetail(HttpServletRequest request) {
        System.out.println("MemberServiceImpl.getMemberInfoDetail");

        // access token 미 입력시
        if (!validateAuthorization(request)) {
            throw new NoExistException(NOT_EXISTS_ACCESS_TOKEN);
        }

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.substring(7, authorization.length());

        Long socialPk = kakaoService.getInformation(accessToken);
        Member member = memberRepository.findMemberBySocialIdAndProvider(socialPk, "kakao");

        if (member == null) {
            throw new NoExistException(NOT_EXISTS_MEMBER);
        }

        Long memberId = member.getMemberId();
        System.out.println("memberId = " + memberId);
        MemberInfoDetail memberInfoDetail = memberRepository.findMemberDetailByMemberId(memberId)
                .orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER));

        List<InterestRes> interests = memberInterestRepository.findByMemberId(memberId);
        memberInfoDetail.setInterests(interests);

        return memberInfoDetail;
    }

    private boolean validateAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization") != null;
    }
}