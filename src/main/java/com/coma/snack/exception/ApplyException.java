package com.coma.snack.exception;

public class ApplyException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}