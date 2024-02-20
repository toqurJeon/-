package com.ssafy.moiroomserver.matching.dto;

import com.ssafy.moiroomserver.matching.entity.MatchingResult;
import com.ssafy.moiroomserver.member.dto.MemberInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchingResultInfo {

	@Getter
	@Setter
	public static class AddMatchingRequest {
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

	@Getter
	@Setter
	public static class AddMatchingResultsRequest {
		private List<AddMatchingRequest> matchingResults;
	}

	@Getter
	@Setter
	public static class MatchingResultResponse {
		private MemberInfo.MatchingResponse member;
		private int matchRate;
		private String matchIntroduction;

		@Builder
		public MatchingResultResponse(MemberInfo.MatchingResponse member, int matchRate, String matchIntroduction) {
			this.member = member;
			this.matchRate = matchRate;
			this.matchIntroduction = matchIntroduction;
		}
	}
}