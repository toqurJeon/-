package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.global.exception.ExistException;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.exception.WrongValueException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.member.dto.*;
import com.ssafy.moiroomserver.member.dto.MemberInfo.AddMemberRequest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.CharacteristicRepository;
import com.ssafy.moiroomserver.member.repository.MemberInterestRepository;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.MemberService;
import com.ssafy.moiroomserver.s3.service.S3Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberInterestRepository memberInterestRepository;
    private final CharacteristicRepository characteristicRepository;

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
        Member member = kakaoService.getMemberByHttpServletRequest(request);
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
    public void login(AddMemberRequest dto) {

        if (memberRepository.existsMemberByProviderAndSocialId(dto.getProvider(), dto.getSocialId())) {
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
    public MemberInfoRes getMemberInfoDetail(HttpServletRequest request) {

        Member member = kakaoService.getMemberByHttpServletRequest(request);
        Long memberId = member.getMemberId();
        Long characteristicId = member.getCharacteristicId();

        MemberInfoDetail memberInfo = memberRepository.findMemberDetailByMemberId(memberId)
                .orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER));
        memberInfo.setCharacteristic(CharacteristicInfo.RequestResponse.builder()
                .characteristic(characteristicRepository.findById(characteristicId)
                        .orElseThrow(() -> new NoExistException(NOT_EXISTS_CHARACTERISTIC)))
                .build());
        memberInfo.setInterests(memberInterestRepository.findByMemberId(memberId));

        return new MemberInfoRes(memberInfo);
    }
}