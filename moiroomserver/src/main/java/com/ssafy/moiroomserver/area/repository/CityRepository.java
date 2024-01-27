package com.ssafy.moiroomserver.area.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moiroomserver.member.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}