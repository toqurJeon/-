package com.ssafy.moiroomserver.matching.dto;

import java.util.List;

import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;

import lombok.Getter;
import lombok.Setter;

public class MatchingInfo {

	@Getter
	@Setter
	public static class GetResponse {
		private CharacteristicInfo.requestResponse memberOne;
		List<CharacteristicInfo.requestResponse> memberTwoList;
	}
}