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

    public void modifyCharacteristicInfo(CharacteristicInfo.CharacteristicResponse characteristicInfoReqRes) {
        this.sociability = (characteristicInfoReqRes.getSociability() == null) ? sociability : characteristicInfoReqRes.getSociability();
        this.positivity = (characteristicInfoReqRes.getPositivity() == null) ? positivity : characteristicInfoReqRes.getPositivity();
        this.activity = (characteristicInfoReqRes.getActivity() == null) ? activity : characteristicInfoReqRes.getActivity();
        this.communion = (characteristicInfoReqRes.getCommunion() == null) ? communion : characteristicInfoReqRes.getCommunion();
        this.altruism = (characteristicInfoReqRes.getAltruism() == null) ? altruism : characteristicInfoReqRes.getAltruism();
        this.empathy = (characteristicInfoReqRes.getEmpathy() == null) ? empathy : characteristicInfoReqRes.getEmpathy();
        this.humor = (characteristicInfoReqRes.getHumor() == null) ? humor : characteristicInfoReqRes.getHumor();
        this.generous = (characteristicInfoReqRes.getGenerous() == null) ? generous : characteristicInfoReqRes.getGenerous();
        this.sleepAt = (characteristicInfoReqRes.getSleepAt() == null) ? sleepAt : characteristicInfoReqRes.getSleepAt();
        this.wakeUpAt = (characteristicInfoReqRes.getWakeUpAt() == null) ? wakeUpAt : characteristicInfoReqRes.getWakeUpAt();
    }
}