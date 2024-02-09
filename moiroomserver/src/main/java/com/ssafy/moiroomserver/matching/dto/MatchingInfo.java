package com.ssafy.moiroomserver.matching.dto;

import java.util.List;

import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class MatchingInfo {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class GetResponse {
		private CharacteristicInfo.RequestResponse memberOne;
		private List<CharacteristicInfo.RequestResponse> memberTwoList;

		@Builder
		public GetResponse(CharacteristicInfo.RequestResponse memberOne, List<CharacteristicInfo.RequestResponse> memberTwoList) {
			this.memberOne = memberOne;
			this.memberTwoList = memberTwoList;
		}
	}

	@Getter
	@Setter
	@ToString
	public static class AddRequest {
		private List<MatchingResultInfo.AddRequest> matchingResultList;
	}
}