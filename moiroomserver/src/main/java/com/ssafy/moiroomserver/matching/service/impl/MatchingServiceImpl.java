package com.ssafy.moiroomserver.matching.service.impl;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;
import static com.ssafy.moiroomserver.global.constants.PageSize.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.moiroomserver.area.repository.CityRepository;
import com.ssafy.moiroomserver.area.repository.MetropolitanRepository;
import com.ssafy.moiroomserver.global.dto.PageResponse;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.dto.MatchingResultInfo;
import com.ssafy.moiroomserver.matching.entity.MatchingResult;
import com.ssafy.moiroomserver.matching.repository.MatchingResultRepository;
import com.ssafy.moiroomserver.matching.service.MatchingService;
import com.ssafy.moiroomserver.member.dto.CharacteristicAndInterestInfo;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.CharacteristicService;

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
	private final MetropolitanRepository metropolitanRepository;
	private final CityRepository cityRepository;
	private final CharacteristicService characteristicService;
	private final KakaoService kakaoService;

	/**
	 * 매칭 계산을 위한 정보 조회
	 * @param request
	 * @return 로그인 사용자와 상대방의 특성 및 관심사 정보
	 */
	@Override
	public MatchingInfo.GetResponse getInfoForMatching(HttpServletRequest request) {
		Member member = kakaoService.getMemberByHttpServletRequest(request);
		//로그인 사용자의 특성 및 관심사 데이터 조회
		CharacteristicAndInterestInfo.RequestResponse memberOne = CharacteristicAndInterestInfo.RequestResponse.builder()
			.memberId(member.getMemberId())
			.characteristic(characteristicService.getCharacteristicOf(member))
			.interests(characteristicService.getInterestListOf(member))
			.build();
		//매칭 상대방의 특성 및 관심사 데이터 조회
		List<Member> matchingMemberList = memberRepository.findByMemberIdNotAndGenderAndMetropolitanIdAndCityIdAndRoommateSearchStatusAndAccountStatus(
			member.getMemberId(), member.getGender(), member.getMetropolitanId(), member.getCityId(), 1, 1);
		List<CharacteristicAndInterestInfo.RequestResponse> memberTwoList = new ArrayList<>();
		for (Member matchingMember : matchingMemberList) {
			CharacteristicAndInterestInfo.RequestResponse memberTwo = CharacteristicAndInterestInfo.RequestResponse.builder()
				.memberId(matchingMember.getMemberId())
				.characteristic(characteristicService.getCharacteristicOf(matchingMember))
				.interests(characteristicService.getInterestListOf(matchingMember))
				.build();
			memberTwoList.add(memberTwo);
		}

		return MatchingInfo.GetResponse.builder()
			.memberOne(memberOne)
			.memberTwos(memberTwoList)
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
		Member member = kakaoService.getMemberByHttpServletRequest(request);
		for (MatchingResultInfo.AddRequest matchingResultInfoAddReq : matchingInfoAddReq.getMatchingResults()) {
			MatchingResult firstMatchingResult = matchingResultRepository.findByMemberOneIdAndMemberTwoId(member.getMemberId(), matchingResultInfoAddReq.getMemberTwoId());
			MatchingResult secondMatchingResult = matchingResultRepository.findByMemberOneIdAndMemberTwoId(matchingResultInfoAddReq.getMemberTwoId(), member.getMemberId());
			//이미 서로의 매칭 결과가 저장되어 있는 경우 -> 수정
			if (firstMatchingResult != null || secondMatchingResult != null) {
				MatchingResult matchingResult = (firstMatchingResult != null) ? firstMatchingResult : secondMatchingResult;
				matchingResult.modifyRating(matchingResultInfoAddReq.getRate(), matchingResultInfoAddReq.getRateIntroduction());
			}
			//아직 서로의 매칭 결과가 저장되어 있지 않는 경우 -> 추가
			if (firstMatchingResult == null && secondMatchingResult == null) {
				matchingResultRepository.save(matchingResultInfoAddReq.toEntity(member.getMemberId()));
			}
		}
	}

	/**
	 * 추천 룸메이트 리스트 조회
	 * @param request
	 * @param pgno 현재 페이지 수
	 * @return 현재 페이지의 추천 룸메이트 리스트
	 */
	@Override
	public PageResponse getMatchingRoommateList(HttpServletRequest request, int pgno) {
		Member member = kakaoService.getMemberByHttpServletRequest(request);
		//현재 페이지의 추천 룸메이트 리스트를 매칭 결과 테이블에서 추출
		PageRequest pageRequest = PageRequest.of(pgno - 1, MATCHING_ROOMMATE_LIST_SIZE);
		Page<MatchingResult> matchingResultPage = matchingResultRepository.findMatchingResultByMemberId(member.getMemberId(), pageRequest);
		if (matchingResultPage.getTotalElements() < 1) {
			return null;
		}
		//매칭 결과 Entity에서 얻은 정보로 추천 룸메이트 응답 DTO 생성
		List<MatchingResultInfo.GetResponse> matchingResultResList = new ArrayList<>();
		for (MatchingResult matchingResult : matchingResultPage.getContent()) {
			Long memberTwoId = (matchingResult.getMemberOneId().equals(member.getMemberId())) ? matchingResult.getMemberTwoId() : matchingResult.getMemberOneId();
			Member memberTwo = memberRepository.findById(memberTwoId)
				.orElseThrow(() -> new NoExistException(NOT_EXISTS_MEMBER_ID));
			String metropolitanName = metropolitanRepository.findNameByMetropolitanId(memberTwo.getMetropolitanId())
				.orElseThrow(() -> new NoExistException(NOT_EXISTS_METROPOLITAN_ID));
			String cityName = cityRepository.findNameByCityId(memberTwo.getCityId())
				.orElseThrow(() -> new NoExistException(NOT_EXISTS_CITY_ID));
			//추천 룸메이트 리스트에 추천 룸메이트 한 명 정보 담기
			matchingResultResList.add(MatchingResultInfo.GetResponse.builder()
				.member(MemberInfo.GetResponse.builder()
					.member(memberTwo)
					.metropolitanName(metropolitanName)
					.cityName(cityName)
					.characteristic(characteristicService.getCharacteristicOf(memberTwo))
					.interests(characteristicService.getInterestListOf(memberTwo))
					.build())
				.matchRate(matchingResult.getRate())
				.matchIntroduction(matchingResult.getRateIntroduction())
				.build());
		}
		return PageResponse.builder()
			.content(matchingResultResList)
			.totalPages(matchingResultPage.getTotalPages())
			.totalElements(matchingResultPage.getTotalElements())
			.currentPage(matchingResultPage.getNumber())
			.pageSize(matchingResultPage.getSize())
			.build();
	}
}