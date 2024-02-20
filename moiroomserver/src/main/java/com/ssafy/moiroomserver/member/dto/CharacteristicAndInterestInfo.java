package com.ssafy.moiroomserver.member.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CharacteristicAndInterestInfo {

	@Getter
	@Setter
	public static class CharactAndInterInfoResponse {
		private Long memberId;
		private CharacteristicInfo.RequestResponse characteristic;
		private List<InterestInfo.RequestResponse> interests;

		@Builder
		public CharactAndInterInfoResponse(Long memberId, CharacteristicInfo.RequestResponse characteristic,
				List<InterestInfo.RequestResponse> interests) {
			this.memberId = memberId;
			this.characteristic = characteristic;
			this.interests = interests;
		}
	}
}