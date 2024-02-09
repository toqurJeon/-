package com.ssafy.moiroomserver.matching.service.impl;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.service.MatchingService;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.dto.InterestInfo;
import com.ssafy.moiroomserver.member.entity.Characteristic;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;
import com.ssafy.moiroomserver.member.repository.CharacteristicRepository;
import com.ssafy.moiroomserver.member.repository.MemberInterestRepository;
import com.ssafy.moiroomserver.member.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

	private final MemberRepository memberRepository;
	private final KakaoService kakaoService;
	private final CharacteristicRepository characteristicRepository;
	private final MemberInterestRepository memberInterestRepository;

	@Override
	public List<MatchingInfo.GetResponse> getInfoForMatching(HttpServletRequest request) {
	   // Long socialId = kakaoService.getInformation(request.getHeader("Authorization").substring(7));
		Member member = memberRepository.findMemberBySocialIdAndProvider(3296727084L, "kakao");
		if (member == null) {
			throw new NoExistException(NOT_EXISTS_MEMBER);
		}
		//로그인 사용자의 특성 및 관심사 데이터 조회
		List<MemberInterest> memberInterestList = memberInterestRepository.findByMember(member);
		List<InterestInfo.RequestResponse> interestInfoResList = new ArrayList<>();
		for (MemberInterest memberInterest : memberInterestList) {
			interestInfoResList.add(InterestInfo.RequestResponse.builder()
				.memberInterest(memberInterest)
				.build());
		}
		Characteristic characteristic = characteristicRepository.findById(member.getCharacteristicId())
			.orElseThrow(() -> new NoExistException(NOT_EXISTS_CHARACTERISTIC_ID));
		CharacteristicInfo.RequestResponse memberOne = CharacteristicInfo.RequestResponse.builder()
			.characteristic(characteristic)
			.memberId(member.getMemberId())
			.interestList(interestInfoResList)
			.build();
		//매칭 상대방의 특성 및 관심사 데이터 조회

		return null;
	}
}