package com.ssafy.moiroomserver.member.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CharacteristicAndInterestInfo {

	@Getter
	@Setter
	public static class RequestResponse {
		private Long memberId;
		private CharacteristicInfo.RequestResponse characteristic;
		private List<InterestInfo.RequestResponse> interestList;

		@Builder
		public RequestResponse(Long memberId, CharacteristicInfo.RequestResponse characteristic,
				List<InterestInfo.RequestResponse> interestList) {
			this.memberId = memberId;
			this.characteristic = characteristic;
			this.interestList = interestList;
		}
	}
}