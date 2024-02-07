package com.ssafy.moiroomserver.global.exception;

import com.ssafy.moiroomserver.global.constants.ErrorCode;

public class WrongValueException extends RuntimeException {

	private final ErrorCode code;

	public WrongValueException() {
		super(""); //고치기
		this.code = null; //고치기
	}

	public WrongValueException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
