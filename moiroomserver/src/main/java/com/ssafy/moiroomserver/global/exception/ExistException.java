package com.ssafy.moiroomserver.global.exception;

import com.ssafy.moiroomserver.global.constants.ErrorCode;
import lombok.Getter;

@Getter
public class ExistException extends RuntimeException {

    private final ErrorCode code;


    public ExistException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
