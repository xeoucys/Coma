package com.coma.snack.dto;

/**
 * 행사 상태
 *
 * 설계: docs/design-server-api.md 4-5
 */
public enum EventStatus {
    UPCOMING, // 오픈 전
    OPEN,     // 진행 중
    CLOSED    // 마감
}
