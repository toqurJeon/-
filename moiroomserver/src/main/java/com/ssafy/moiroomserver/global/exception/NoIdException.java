package com.ssafy.moiroomserver.global.exception;

import com.ssafy.moiroomserver.global.constants.ErrorCode;
import lombok.Getter;

@Getter
public class NoIdException extends RuntimeException {

    private final ErrorCode code;

    public NoIdException() {
        super(ErrorCode.NOT_EXISTS_ID.getMessage());
        this.code = ErrorCode.NOT_EXISTS_ID;
    }

    public NoIdException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}