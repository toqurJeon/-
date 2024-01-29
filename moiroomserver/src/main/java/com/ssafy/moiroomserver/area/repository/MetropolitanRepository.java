package com.ssafy.moiroomserver.area.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moiroomserver.area.entity.Metropolitan;

@Repository
public interface MetropolitanRepository extends JpaRepository<Metropolitan, Long> {
}