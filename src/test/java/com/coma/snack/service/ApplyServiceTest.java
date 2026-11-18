package com.coma.snack.service;

/**
 * 동시 신청 테스트 (담당: 오지우)
 *
 * TODO (CountDownLatch로 여러 스레드를 동시에 출발)
 * - 정원 1에 10명 동시 신청 → 성공 1명, SOLD_OUT 9명
 * - 정원 5에 10명 동시 신청 → 성공 5명, 남은 수량 0
 * - 같은 학번으로 5번 동시 신청 → 성공 1번, DUPLICATE 4번
 *
 * 설계: docs/design-server-api.md 4-4
 */
class ApplyServiceTest {
}
