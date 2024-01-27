package com.ssafy.moiroomserver.area.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moiroomserver.area.service.AreaService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class AreaController {

	private final AreaService areaService;
}