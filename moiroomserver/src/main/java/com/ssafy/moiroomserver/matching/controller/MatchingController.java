package com.ssafy.moiroomserver.matching.controller;

import com.ssafy.moiroomserver.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
}