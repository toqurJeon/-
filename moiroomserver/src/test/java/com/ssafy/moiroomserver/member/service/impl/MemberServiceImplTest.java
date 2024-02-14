package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.member.dto.AddMemberDto;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class MemberServiceImplTest {

    @Autowired MemberRepository memberRepository;

    @Test
    void modifyMemberInfo() {
    }

    @Test
    @Transactional
    void login() {
        //Given
        AddMemberDto dto = new AddMemberDto(
                12345L,
                "kakao",
                "nicknameTest1",
                "imageUrlTest1",
                "1999",
                "0613",
                "nameTest1",
                "male",
                "accessToken1",
                "refreshToken1"
        );

        //When
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

        //Then
        Assertions.assertThat(memberRepository.findMemberBySocialIdAndProvider(dto.getSocialId(), dto.getProvider()))
                .isNull();
    }

    @Test
    void modifyMemberToken() {
    }

    @Test
    void getMemberById() {
    }

    @Test
    void logout() {
    }

    @Test
    void getMemberInfoDetail() {
    }
}