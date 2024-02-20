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
	public static class MatchingResponse {
		private CharacteristicAndInterestInfo.CharactAndInterInfoResponse memberOne;
		private List<CharacteristicAndInterestInfo.CharactAndInterInfoResponse> memberTwos;

		@Builder
		public MatchingResponse(CharacteristicAndInterestInfo.CharactAndInterInfoResponse memberOne,
				List<CharacteristicAndInterestInfo.CharactAndInterInfoResponse> memberTwos) {
			this.memberOne = memberOne;
			this.memberTwos = memberTwos;
		}
	}

}