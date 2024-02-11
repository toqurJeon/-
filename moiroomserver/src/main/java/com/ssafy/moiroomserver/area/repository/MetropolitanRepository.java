package com.ssafy.moiroomserver.area.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moiroomserver.area.entity.Metropolitan;

@Repository
public interface MetropolitanRepository extends JpaRepository<Metropolitan, Long> {

	Optional<String> findNameByMetropolitanId(Long metropolitanId);
}