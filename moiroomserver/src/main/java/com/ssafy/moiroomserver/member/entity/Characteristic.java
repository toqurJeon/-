package com.ssafy.moiroomserver.member.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "characteristic")
public class Characteristic extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characteristicsId;

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

    @Builder
    public Characteristic(int sociability, int positivity, int activity, int communion, int altruism, int empathy, int humor, int generous, String sleepAt, String wakeUpAt) {
        this.sociability = sociability;
        this.positivity = positivity;
        this.activity = activity;
        this.communion = communion;
        this.altruism = altruism;
        this.empathy = empathy;
        this.humor = humor;
        this.generous = generous;
        this.sleepAt = sleepAt;
        this.wakeUpAt = wakeUpAt;
    }
}