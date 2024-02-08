package com.ssafy.moiroomserver.area.service.impl;

import com.ssafy.moiroomserver.area.dto.GetCity;
import com.ssafy.moiroomserver.area.dto.GetMetropolitan;
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
	public List<GetMetropolitan> getMetropolitans() {
		List<Metropolitan> metropolitans = metropolitanRepository.findAll();
		List<GetMetropolitan> metropolitansDto = new ArrayList<>();

		for (Metropolitan metropolitan : metropolitans) {
			metropolitansDto.add(new GetMetropolitan(metropolitan.getMetropolitanId()
					, metropolitan.getName()));
		}

		return metropolitansDto;
	}

	@Override
	public List<GetCity> getCitiesByMetropolitanId(Long metropolitanId) {
		List<GetCity> citiesDto = cityRepository.findCitiesByMetropolitanId(metropolitanId);

		return citiesDto;
	}
}