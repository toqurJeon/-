package com.ssafy.moiroomserver.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "city")
public class City {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metropolitan_id", nullable = false)
    private Metropolitan metropolitan;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(name = "name", nullable = false)
    private String name;
}