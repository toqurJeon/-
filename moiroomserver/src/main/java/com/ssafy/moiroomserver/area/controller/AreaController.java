package com.ssafy.moiroomserver.area.controller;

import com.ssafy.moiroomserver.area.dto.GetCity;
import com.ssafy.moiroomserver.area.dto.GetMetropolitan;
import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;

import com.ssafy.moiroomserver.area.service.AreaService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/area")
public class AreaController {

	private final AreaService areaService;

	/**
	 * 광역시 조회 api
	 * @return
	 */
	@GetMapping("/metropolitan")
	public ApiResponse<?> getMetropolitans() {
		List<GetMetropolitan> metropolitans = areaService.getMetropolitans();
		return ApiResponse.success(SuccessCode.GET_METROPOLITANS, metropolitans);
	}

	/**
	 * 시군구 조회 api
	 * @return
	 */
	@GetMapping("/city")
	public ApiResponse<?> getCities() {
		List<GetCity> cities = areaService.getCities();
		return ApiResponse.success(SuccessCode.GET_METROPOLITANS, cities);
	}
}