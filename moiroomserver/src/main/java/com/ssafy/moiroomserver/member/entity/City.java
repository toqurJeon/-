package com.ssafy.moiroomserver.member.entity;

import jakarta.persistence.*;

@Entity
public class City {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
}