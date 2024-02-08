package com.ssafy.moiroomserver.global.exception;

import com.ssafy.moiroomserver.global.constants.ErrorCode;
import lombok.Getter;

@Getter
public class WrongValueException extends RuntimeException {

	private final ErrorCode code;

	public WrongValueException() {
		super(ErrorCode.WRONG_VALUE.getMessage());
		this.code = ErrorCode.WRONG_VALUE;
	}

	public WrongValueException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
