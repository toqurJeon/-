package com.ssafy.moiroomserver.member.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
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
    private Integer sociability;

    @Column(name = "positivity")
    private Integer positivity;

    @Column(name = "activity")
    private Integer activity;

    @Column(name = "communion")
    private Integer communion;

    @Column(name = "altruism")
    private Integer altruism;

    @Column(name = "empathy")
    private Integer empathy;

    @Column(name = "humor")
    private Integer humor;

    @Column(name = "generous")
    private Integer generous;

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

    public void modifyCharacteristicInfo(CharacteristicInfo.AddModifyRequest characteristicAddModifyReq) {
        this.sociability = (characteristicAddModifyReq.getSociability() == null) ? sociability : characteristicAddModifyReq.getSociability();
        this.positivity = (characteristicAddModifyReq.getPositivity() == null) ? positivity : characteristicAddModifyReq.getPositivity();
        this.activity = (characteristicAddModifyReq.getActivity() == null) ? activity : characteristicAddModifyReq.getActivity();
        this.communion = (characteristicAddModifyReq.getCommunion() == null) ? communion : characteristicAddModifyReq.getCommunion();
        this.altruism = (characteristicAddModifyReq.getAltruism() == null) ? altruism : characteristicAddModifyReq.getAltruism();
        this.empathy = (characteristicAddModifyReq.getEmpathy() == null) ? empathy : characteristicAddModifyReq.getEmpathy();
        this.humor = (characteristicAddModifyReq.getHumor() == null) ? humor : characteristicAddModifyReq.getHumor();
        this.generous = (characteristicAddModifyReq.getGenerous() == null) ? generous : characteristicAddModifyReq.getGenerous();
        this.sleepAt = (characteristicAddModifyReq.getSleepAt() == null) ? sleepAt : characteristicAddModifyReq.getSleepAt();
        this.wakeUpAt = (characteristicAddModifyReq.getWakeUpAt() == null) ? wakeUpAt : characteristicAddModifyReq.getWakeUpAt();
    }
}