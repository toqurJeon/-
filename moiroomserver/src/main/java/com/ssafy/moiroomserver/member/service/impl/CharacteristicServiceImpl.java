package com.ssafy.moiroomserver.member.service.impl;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.kakao.KakaoService;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.dto.InterestInfo;
import com.ssafy.moiroomserver.member.entity.Characteristic;
import com.ssafy.moiroomserver.member.entity.Interest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;
import com.ssafy.moiroomserver.member.repository.CharacteristicRepository;
import com.ssafy.moiroomserver.member.repository.InterestRepository;
import com.ssafy.moiroomserver.member.repository.MemberInterestRepository;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.CharacteristicService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

	private final MemberRepository memberRepository;
	private final CharacteristicRepository characteristicRepository;
	private final MemberInterestRepository memberInterestRepository;
	private final InterestRepository interestRepository;
	private final KakaoService kakaoService;

	/**
	 * 특성 및 관심사 데이터 추가 및 수정
	 * @param request
	 * @param characteristicInfoAddModifyReq 추가 및 수정 시 입력할 데이터
	 */
	@Transactional
	@Override
	public void addCharacteristic(HttpServletRequest request, CharacteristicInfo.RequestResponse characteristicInfoAddModifyReq) {
		// Long socialId = kakaoService.getInformation(request.getHeader("Authorization").substring(7));
		Member member = memberRepository.findMemberBySocialIdAndProvider(3296727084L, "kakao");
		if (member == null) {
			throw new NoExistException(NOT_EXISTS_MEMBER);
		}
		if (member.getCharacteristicId() == null) { //특성 및 관심사 첫 데이터 입력 (회원가입 후 첫 매칭 시작)
			Characteristic characteristic = characteristicRepository.save(characteristicInfoAddModifyReq.toEntity()); //특성 데이터 추가
			member.modifyCharacteristicId(characteristic.getCharacteristicsId()); //추가한 특성 아이디 회원 테이블의 특성 아이디 컬럼에 추가
		}
		if (member.getCharacteristicId() != null) { //특성 및 관심사 데이터 수정 (기존 특성 데이터 존재)
			Characteristic characteristic = characteristicRepository.findById(member.getCharacteristicId())
				.orElseThrow(() -> new NoExistException(NOT_EXISTS_CHARACTERISTIC_ID)); //기존의 특성 데이터 찾기
			characteristic.modifyCharacteristicInfo(characteristicInfoAddModifyReq); //특성 데이터 수정
			memberInterestRepository.deleteByMember(member); //기존의 관심사 데이터 전부 삭제
		}
		//관심사 데이터 추가 (특성 및 관심사 데이터 추가 및 수정 작업 모든 경우에 실행)
		if (characteristicInfoAddModifyReq.getInterestList() == null) return;
		for (InterestInfo.RequestResponse interestAddReq : characteristicInfoAddModifyReq.getInterestList()) {
			Interest interest = interestRepository.findByName(interestAddReq.getInterestName() + "(sample)");
			if (interest == null) {
				throw new NoExistException(NOT_EXISTS_INTEREST_NAME);
			}
			memberInterestRepository.save(interestAddReq.toEntity(member, interest));
		}
	}

	/**
	 * 회원의 특성 및 관심사 정보 조회
	 * @param member 조회 대상이 되는 회원
	 * @return 회원의 특성 및 관심사 정보
	 */
	@Override
	public CharacteristicInfo.RequestResponse getCharacteristicAndInterestOfMember(Member member) {
		List<MemberInterest> memberInterestList = memberInterestRepository.findByMember(member);
		List<InterestInfo.RequestResponse> interestInfoResList = new ArrayList<>();
		for (MemberInterest memberInterest : memberInterestList) {
			interestInfoResList.add(InterestInfo.RequestResponse.builder()
				.memberInterest(memberInterest)
				.build());
		}
		Characteristic characteristic = characteristicRepository.findById(member.getCharacteristicId())
			.orElseThrow(() -> new NoExistException(NOT_EXISTS_CHARACTERISTIC_ID));

		return CharacteristicInfo.RequestResponse.builder()
			.characteristic(characteristic)
			.memberId(member.getMemberId())
			.interestList(interestInfoResList)
			.build();
	}
}