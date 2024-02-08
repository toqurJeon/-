package com.ssafy.moiroomserver.member.repository;

import com.ssafy.moiroomserver.member.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

    Interest findByName(String name);
}