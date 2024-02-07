package com.ssafy.moiroomserver.matching.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "matching_result")
public class MatchingResult {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingResultId;

    @Column(name = "member_id", nullable = false)
    private Long memberOneId;

    @Column(name = "member_two_id", nullable = false)
    private Long memberTwoId;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "rate_introduction", nullable = false)
    private String rateIntroduction;
}
