package com.ssafy.moiroomserver.global.config;

import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.global.exception.ExistException;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.global.exception.WrongValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ApiResponse<?> handleNoExistException(NoExistException e) {

        return ApiResponse.error(e.getCode());
    }

    @ExceptionHandler
    public ApiResponse<?> handleExistException(ExistException e) {

        return ApiResponse.error(e.getCode());
    }

    @ExceptionHandler
    public ApiResponse<?> handleWrongValueException(WrongValueException e) {

        return ApiResponse.error(e.getCode());
    }
}