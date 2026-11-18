package com.coma.snack.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ApplyException을 실패 응답 JSON으로 바꿔 줌 (담당: 오지우)
 *
 * TODO
 * - @ExceptionHandler(ApplyException.class)
 *   → { "success": false, "code": "...", "message": "..." }
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
}
