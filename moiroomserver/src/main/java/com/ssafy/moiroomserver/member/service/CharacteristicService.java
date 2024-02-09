package com.ssafy.moiroomserver.member.service;

import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.entity.Member;

import jakarta.servlet.http.HttpServletRequest;

public interface CharacteristicService {

	void addCharacteristic(HttpServletRequest request, CharacteristicInfo.RequestResponse characteristicInfoAddModifyReq);
	CharacteristicInfo.RequestResponse getCharacteristicAndInterestOfMember(Member member);
}