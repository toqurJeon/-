package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.member.dto.MemberInfo.AddMemberRequest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void modifyMemberInfo() {
    }

    @Test
    @Transactional
    void login() {
        //Given
        AddMemberRequest dto = new AddMemberRequest(
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

//        Then
        assertThat(memberRepository.findMemberBySocialIdAndProvider(dto.getSocialId(), dto.getProvider()))
                .isNotNull();
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