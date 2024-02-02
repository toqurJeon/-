package com.ssafy.moiroomserver.member.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "character")
public class Character extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @Column(name = "sociability")
    private int sociability;

    @Column(name = "positivity")
    private int positivity;

    @Column(name = "activity")
    private int activity;

    @Column(name = "communion")
    private int communion;

    @Column(name = "altruism")
    private int altruism;

    @Column(name = "empathy")
    private int empathy;

    @Column(name = "humor")
    private int humor;

    @Column(name = "generous")
    private int generous;

    @Column(name = "sleep_at")
    private String sleepAt;

    @Column(name = "wake_up_at")
    private String wakeUpAt;
}