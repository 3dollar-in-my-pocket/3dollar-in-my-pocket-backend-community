package com.threedollar.common.exception;

public class InvalidRequestException extends BaseException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.E400_INVALID;

    public InvalidRequestException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }

}
