package com.ssafy.moiroomserver.area.service.impl;

import com.ssafy.moiroomserver.area.dto.GetCityResponse;
import com.ssafy.moiroomserver.area.dto.GetMetropolitanResponse;
import com.ssafy.moiroomserver.area.entity.Metropolitan;
import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.area.repository.CityRepository;
import com.ssafy.moiroomserver.area.repository.MetropolitanRepository;
import com.ssafy.moiroomserver.area.service.AreaService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

	private final MetropolitanRepository metropolitanRepository;
	private final CityRepository cityRepository;

	@Override
	public List<GetMetropolitanResponse> getMetropolitans() {
		List<Metropolitan> metropolitans = metropolitanRepository.findAll();
		List<GetMetropolitanResponse> metropolitansDto = new ArrayList<>();

		for (Metropolitan metropolitan : metropolitans) {
			metropolitansDto.add(new GetMetropolitanResponse(metropolitan.getMetropolitanId()
					, metropolitan.getName()));
		}

		return metropolitansDto;
	}

	@Override
	public List<GetCityResponse> getCitiesByMetropolitanId(Long metropolitanId) {
		List<GetCityResponse> citiesDto = cityRepository.findCitiesByMetropolitanId(metropolitanId);

		return citiesDto;
	}
}