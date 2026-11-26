package com.coma.snack.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.coma.snack.dto.ApplyResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Service에서 던진 ApplyException
    @ExceptionHandler(ApplyException.class)
    public ResponseEntity<ApplyResponse> handleApplyException(ApplyException e) {
        return makeResponse(e.getErrorCode());
    }

    // DB 오류
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApplyResponse> handleDataAccessException(DataAccessException e) {
        e.printStackTrace();
        return makeResponse(ErrorCode.SERVER_ERROR);
    }
    
    // JSON 형식이 틀렸을 때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApplyResponse> handleNotReadable(HttpMessageNotReadableException e) {
        return makeResponse(ErrorCode.INVALID_INPUT);
    }

    private ResponseEntity<ApplyResponse> makeResponse(ErrorCode errorCode) {
        ApplyResponse response = new ApplyResponse();
        response.setSuccess(false);
        response.setCode(errorCode.name());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}