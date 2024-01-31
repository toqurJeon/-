package com.ssafy.moiroomserver.global.config;

import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.global.exception.NoIdException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ApiResponse<?> handleNoIdException(NoIdException e) {

        return ApiResponse.error(e.getCode());
    }
}