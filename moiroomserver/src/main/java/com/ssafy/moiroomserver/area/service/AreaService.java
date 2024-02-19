package com.ssafy.moiroomserver.area.service;

import com.ssafy.moiroomserver.area.dto.GetCityResponse;
import com.ssafy.moiroomserver.area.dto.GetMetropolitanResponse;

import java.util.List;

public interface AreaService {
    List<GetMetropolitanResponse> getMetropolitans();

    List<GetCityResponse> getCitiesByMetropolitanId(Long metropolitanId);
}