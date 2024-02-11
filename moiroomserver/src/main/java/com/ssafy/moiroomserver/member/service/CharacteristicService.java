package com.ssafy.moiroomserver.member.service;

import java.util.List;

import com.ssafy.moiroomserver.member.dto.CharacteristicAndInterestInfo;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.dto.InterestInfo;
import com.ssafy.moiroomserver.member.entity.Member;

import jakarta.servlet.http.HttpServletRequest;

public interface CharacteristicService {

	void addCharacteristic(HttpServletRequest request, CharacteristicAndInterestInfo.RequestResponse infoAddModifyReq);
	CharacteristicInfo.RequestResponse getCharacteristicOf(Member member);
	List<InterestInfo.RequestResponse> getInterestListOf(Member member);
}