package com.ssafy.moiroomserver.matching.dto;

import com.ssafy.moiroomserver.matching.entity.MatchingResult;

import lombok.Getter;
import lombok.Setter;

public class MatchingResultInfo {

	@Getter
	@Setter
	public static class AddRequest {
		private Long memberTwoId;
		private int rate;
		private String rateIntroduction;

		public MatchingResult toEntity(Long memberOneId) {

			return MatchingResult.builder()
				.memberOneId(memberOneId)
				.memberTwoId(memberTwoId)
				.rate(rate)
				.rateIntroduction(rateIntroduction)
				.build();
		}
	}
}