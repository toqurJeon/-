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
		private List<CharacteristicAndInterestInfo.RequestResponse> memberTwoList;

		@Builder
		public GetResponse(CharacteristicAndInterestInfo.RequestResponse memberOne,
				List<CharacteristicAndInterestInfo.RequestResponse> memberTwoList) {
			this.memberOne = memberOne;
			this.memberTwoList = memberTwoList;
		}
	}

	@Getter
	@Setter
	public static class AddRequest {
		private List<MatchingResultInfo.AddRequest> matchingResultList;
	}
}