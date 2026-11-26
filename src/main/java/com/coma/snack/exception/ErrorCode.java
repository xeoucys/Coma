package com.coma.snack.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값을 다시 확인해주세요."),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 행사입니다."),
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."),
    NOT_OPEN(HttpStatus.CONFLICT, "아직 신청 기간이 아닙니다."),
    CLOSED(HttpStatus.CONFLICT, "마감된 행사입니다."),
    DUPLICATE(HttpStatus.CONFLICT, "이미 신청한 행사입니다."),
    SOLD_OUT(HttpStatus.CONFLICT, "품절된 메뉴입니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "잠시 후 다시 시도해주세요.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}