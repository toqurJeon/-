package com.ssafy.moiroomserver.member.service.impl;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.moiroomserver.global.kakao.KakaoService;
import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.member.dto.CharacteristicAndInterestInfo;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.dto.InterestInfo;
import com.ssafy.moiroomserver.member.entity.Characteristic;
import com.ssafy.moiroomserver.member.entity.Interest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;
import com.ssafy.moiroomserver.member.repository.CharacteristicRepository;
import com.ssafy.moiroomserver.member.repository.InterestRepository;
import com.ssafy.moiroomserver.member.repository.MemberInterestRepository;
import com.ssafy.moiroomserver.member.service.CharacteristicService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

	private final CharacteristicRepository characteristicRepository;
	private final MemberInterestRepository memberInterestRepository;
	private final InterestRepository interestRepository;
	private final KakaoService kakaoService;

	/**
	 * 특징 및 관심사 데이터 추가 및 수정
	 * @param request
	 * @param infoAddModifyReq 추가 및 수정 시 입력할 데이터
	 */
	@Transactional
	@Override
	public void addCharacteristic(HttpServletRequest request, CharacteristicAndInterestInfo.RequestResponse infoAddModifyReq) {
		System.out.println("특성 메소드 내 진입");
		Member member = kakaoService.getMemberByHttpServletRequest(request);
		System.out.println("특성 메소드 내 Member 정보 : " + member.toString());
		System.out.println("특성 및 관심사 데이터 추가 시작");
		if (member.getCharacteristicId() == null) { //특성 및 관심사 첫 데이터 입력 (회원가입 후 첫 매칭 시작)
			Characteristic characteristic = characteristicRepository.save(infoAddModifyReq.getCharacteristic().toEntity()); //특성 데이터 추가
			member.modifyCharacteristicId(characteristic.getCharacteristicsId()); //추가한 특성 아이디 회원 테이블의 특성 아이디 컬럼에 추가
			System.out.println("첫 특성 데이터 추가 끝");
		}
		if (member.getCharacteristicId() != null) { //특성 및 관심사 데이터 수정 (기존 특성 데이터 존재)
			Characteristic characteristic = characteristicRepository.findById(member.getCharacteristicId())
				.orElseThrow(() -> new NoExistException(NOT_EXISTS_CHARACTERISTIC)); //기존의 특성 데이터 찾기
			characteristic.modifyCharacteristicInfo(infoAddModifyReq.getCharacteristic()); //특성 데이터 수정
			memberInterestRepository.deleteByMember(member); //기존의 관심사 데이터 전부 삭제
			System.out.println("두 번재 이후 특성 데이터 추가 끝");
		}
		//관심사 데이터 추가 (특성 및 관심사 데이터 추가 및 수정 작업 모든 경우에 실행)
		if (infoAddModifyReq.getInterests() == null) return;
		for (InterestInfo.RequestResponse interestAddReq : infoAddModifyReq.getInterests()) {
			Interest interest = interestRepository.findByName(interestAddReq.getInterestName() + "(sample)") //나중에 (sample) 없애기
				.orElseThrow(() -> new NoExistException(NOT_EXISTS_INTEREST_NAME));
			memberInterestRepository.save(interestAddReq.toEntity(member, interest));
		}
		System.out.println("특성 및 관심사 데이터 추가 끝");
	}

	/**
	 * 회원의 특징 정보 조회
	 * @param member 조회 대상이 되는 회원
	 * @return 회원의 특징 정보
	 */
	@Override
	public CharacteristicInfo.RequestResponse getCharacteristicOf(Member member) {
		if (member.getCharacteristicId() == null) {
			throw new NoExistException(NOT_EXIST_CHARACTERISTIC_ID);
		}
		Characteristic characteristic = characteristicRepository.findById(member.getCharacteristicId())
			.orElseThrow(() -> new NoExistException(NOT_EXISTS_CHARACTERISTIC));

		return CharacteristicInfo.RequestResponse.builder()
			.characteristic(characteristic)
			.build();
	}

	/**
	 * 회원의 관심사 정보 조회
	 * @param member 조회 대상이 되는 회원
	 * @return 회원의 관심사 정보
	 */
	@Override
	public List<InterestInfo.RequestResponse> getInterestListOf(Member member) {
		List<MemberInterest> memberInterestList = memberInterestRepository.findByMemberOrderByPercentDesc(member);
		List<InterestInfo.RequestResponse> interestInfoResList = new ArrayList<>();
		for (MemberInterest memberInterest : memberInterestList) {
			interestInfoResList.add(InterestInfo.RequestResponse.builder()
				.memberInterest(memberInterest)
				.build());
		}
		return  interestInfoResList;
	}
}