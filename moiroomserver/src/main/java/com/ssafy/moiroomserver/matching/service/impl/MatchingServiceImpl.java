package com.ssafy.moiroomserver.matching.service.impl;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.service.MatchingService;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.CharacteristicService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

	private final MemberRepository memberRepository;
	private final KakaoService kakaoService;
	private final CharacteristicService characteristicService;

	/**
	 * 매칭을 위한 정보 조회
	 * @param request
	 * @return 로그인 사용자와 상대방의 특성 및 관심사 정보
	 */
	@Override
	public MatchingInfo.GetResponse getInfoForMatching(HttpServletRequest request) {
	   // Long socialId = kakaoService.getInformation(request.getHeader("Authorization").substring(7));
		Member member = memberRepository.findMemberBySocialIdAndProvider(3296727084L, "kakao");
		if (member == null) {
			throw new NoExistException(NOT_EXISTS_MEMBER);
		}
		//로그인 사용자의 특성 및 관심사 데이터 조회
		CharacteristicInfo.RequestResponse memberOne = characteristicService.getCharacteristicAndInterestOfMember(member);
		//매칭 상대방의 특성 및 관심사 데이터 조회
		List<Member> matchingMemberList = memberRepository.findByMemberIdNotAndGenderAndMetropolitanIdAndCityIdAndRoommateSearchStatus(
			member.getMemberId(), member.getGender(), member.getMetropolitanId(), member.getCityId(), 1);
		List<CharacteristicInfo.RequestResponse> memberTwoList = new ArrayList<>();
		for (Member matchingMember : matchingMemberList) {
			CharacteristicInfo.RequestResponse memberTwo = characteristicService.getCharacteristicAndInterestOfMember(matchingMember);
			memberTwoList.add(memberTwo);
		}

		return MatchingInfo.GetResponse.builder()
			.memberOne(memberOne)
			.memberTwoList(memberTwoList)
			.build();
	}

	@Override
	public void addMatchingResult(HttpServletRequest request, MatchingInfo.AddRequest matchingInfoAddReq) {
		// Long socialId = kakaoService.getInformation(request.getHeader("Authorization").substring(7));
		Member member = memberRepository.findMemberBySocialIdAndProvider(3296727084L, "kakao");
		if (member == null) {
			throw new NoExistException(NOT_EXISTS_MEMBER);
		}
	}
}