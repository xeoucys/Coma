package com.coma.snack.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 신청 API (담당: 오지우)
 *
 * TODO
 * - POST /api/events/{eventId}/applications → ApplyResponse  (R3~R5)
 * - 입력값 검사 후 ApplyService.apply() 호출
 * - apply()가 끝난 뒤(커밋 후) StockNotifier로 수량 알림
 *
 * 설계: docs/design-server-api.md 4-2, 4-4
 */
@RestController
@RequestMapping("/api")
public class ApplyController {
}
