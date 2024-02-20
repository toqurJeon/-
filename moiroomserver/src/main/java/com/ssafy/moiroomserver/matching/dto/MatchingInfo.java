package com.ssafy.moiroomserver.matching.dto;

import java.util.List;

import com.ssafy.moiroomserver.member.dto.CharacteristicAndInterestInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MatchingInfo {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class GetResponse {
		private CharacteristicAndInterestInfo.RequestResponse memberOne;
		private List<CharacteristicAndInterestInfo.RequestResponse> memberTwos;

		@Builder
		public GetResponse(CharacteristicAndInterestInfo.RequestResponse memberOne,
				List<CharacteristicAndInterestInfo.RequestResponse> memberTwos) {
			this.memberOne = memberOne;
			this.memberTwos = memberTwos;
		}
	}

	@Getter
	@Setter
	public static class AddRequest {
		private List<MatchingResultInfo.AddMatchingRequest> matchingResults;
	}
}