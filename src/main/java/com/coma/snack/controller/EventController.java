package com.coma.snack.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 행사 조회 API (담당: 오지우)
 *
 * TODO
 * - GET /api/events             → List<EventSummaryDto>  (R1)
 * - GET /api/events/{eventId}   → EventDetailDto         (R2, R4)
 *
 * 설계: docs/design-server-api.md 4-2
 */
@RestController
@RequestMapping("/api/events")
public class EventController {
}
