package com.ssafy.moiroomserver.area.repository;

import com.ssafy.moiroomserver.area.dto.GetCityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.moiroomserver.area.entity.City;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select new com.ssafy.moiroomserver.area.dto.GetCity(c.cityId, c.metropolitan.metropolitanId, c.name) " +
            "from City c where c.metropolitan.metropolitanId = :metropolitanId")
    List<GetCityResponse> findCitiesByMetropolitanId(Long metropolitanId);
    @Query("SELECT name FROM City WHERE cityId = :cityId")
    Optional<String> findNameByCityId(Long cityId);
}