package com.ssafy.moiroomserver.area.service.impl;

import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.area.repository.CityRepository;
import com.ssafy.moiroomserver.area.repository.MetropolitanRepository;
import com.ssafy.moiroomserver.area.service.AreaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

	private final MetropolitanRepository metropolitanRepository;
	private final CityRepository cityRepository;
}