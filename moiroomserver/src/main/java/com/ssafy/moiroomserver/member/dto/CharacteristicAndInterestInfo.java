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
		private CharacteristicInfo.CharacteristicResponse characteristic;
		private List<InterestInfo.InterestInfoResponse> interests;

		@Builder
		public CharactAndInterInfoResponse(Long memberId, CharacteristicInfo.CharacteristicResponse characteristic,
				List<InterestInfo.InterestInfoResponse> interests) {
			this.memberId = memberId;
			this.characteristic = characteristic;
			this.interests = interests;
		}
	}
	@Getter
	@Setter
	public static class AddCharactAndInterInfoRequest {
		private Long memberId;
		private CharacteristicInfo.CharacteristicResponse characteristic;
		private List<InterestInfo.InterestInfoResponse> interests;
	}

}