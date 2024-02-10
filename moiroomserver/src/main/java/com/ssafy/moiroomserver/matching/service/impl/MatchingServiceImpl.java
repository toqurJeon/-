package com.ssafy.moiroomserver.matching.service.impl;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;
import static com.ssafy.moiroomserver.global.constants.PageSize.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.moiroomserver.global.dto.PageResponse;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.dto.MatchingResultInfo;
import com.ssafy.moiroomserver.matching.entity.MatchingResult;
import com.ssafy.moiroomserver.matching.repository.MatchingResultRepository;
import com.ssafy.moiroomserver.matching.service.MatchingService;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.CharacteristicService;
import com.ssafy.moiroomserver.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

	private final MemberRepository memberRepository;
	private final MatchingResultRepository matchingResultRepository;
	private final CharacteristicService characteristicService;
	private final MemberService memberService;

	/**
	 * 매칭 계산을 위한 정보 조회
	 * @param request
	 * @return 로그인 사용자와 상대방의 특성 및 관심사 정보
	 */
	@Override
	public MatchingInfo.GetResponse getInfoForMatching(HttpServletRequest request) {
		Member member = memberService.getMemberByHttpServletRequest(request);
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

	/**
	 * 매칭 결과 추가
	 * @param request
	 * @param matchingInfoAddReq 추가할 매칭 결과 리스트
	 */
	@Transactional
	@Override
	public void addMatchingResult(HttpServletRequest request, MatchingInfo.AddRequest matchingInfoAddReq) {
		Member member = memberService.getMemberByHttpServletRequest(request);
		for (MatchingResultInfo.AddRequest matchingResultInfoAddReq : matchingInfoAddReq.getMatchingResultList()) {
			matchingResultRepository.save(matchingResultInfoAddReq.toEntity(member.getMemberId()));
		}
	}

	@Override
	public PageResponse getMatchingRoommateList(HttpServletRequest request, int pgno) {
		// Long socialId = kakaoService.getInformation(request.getHeader("Authorization").substring(7));
		Member member = memberRepository.findMemberBySocialIdAndProvider(3296727084L, "kakao");
		if (member == null) {
			throw new NoExistException(NOT_EXISTS_MEMBER);
		}
		PageRequest pageRequest = PageRequest.of(pgno - 1, MATCHING_ROOMMATE_LIST_SIZE);
		// Page<MatchingResult> matchingResultPage = matchingResultRepository.findByMemberOneIdOrMemberTwoIdOrderByRateDesc(member.getMemberId());
		// if (matchingResultPage.getTotalElements() < 1) {
		// 	return null;
		// }
		// for (MatchingResult matchingResult : matchingResultPage.getContent()) {
		// 	Long memberTwoId = (matchingResult.getMemberOneId().equals(member.getMemberId())) ? matchingResult.getMemberTwoId() : matchingResult.getMemberOneId();
		// 	Member memberTwo = memberRepository.findById(memberTwoId)
		// 		.orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER_ID));
		// 	List<MemberInfo.GetDetailResponse> memberInfoGetDetailResList = new ArrayList<>();
		// }
		return null;
	}
}