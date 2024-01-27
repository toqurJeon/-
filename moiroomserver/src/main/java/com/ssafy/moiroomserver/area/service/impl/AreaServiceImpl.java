package com.ssafy.moiroomserver.area.service.impl;

import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.area.service.AreaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

	private final AreaRepository areaRepository;
}