package com.ssafy.moiroomserver.matching.dto;

import lombok.Getter;
import lombok.Setter;

public class MatchingResultInfo {

	@Getter
	@Setter
	public static class AddRequest {
		private Long memberTwoId;
		private int rate;
		private String rateIntroduction;
	}
}