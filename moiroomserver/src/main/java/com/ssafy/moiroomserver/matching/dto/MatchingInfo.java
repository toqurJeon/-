package com.ssafy.moiroomserver.matching.dto;

import java.util.List;

import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MatchingInfo {

	@Getter
	@Setter
	public static class GetResponse {
		private CharacteristicInfo.RequestResponse memberOne;
		private List<CharacteristicInfo.RequestResponse> memberTwoList;

		@Builder
		public GetResponse(CharacteristicInfo.RequestResponse memberOne, List<CharacteristicInfo.RequestResponse> memberTwoList) {
			this.memberOne = memberOne;
			this.memberTwoList = memberTwoList;
		}
	}
}