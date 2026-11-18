package com.coma.snack.service;

import org.springframework.stereotype.Service;

/**
 * 신청 처리, 정원 차감, 동시 신청 처리 (담당: 오지우)
 *
 * TODO
 * - apply(Long eventId, ApplyRequest req): ApplyResponse  (@Transactional)
 *   1. 행사·옵션 확인  2. 신청 기간 확인  3. 중복 신청 확인
 *   4. OptionDao.decreaseRemaining() → 0행이면 SOLD_OUT
 *   5. ApplicationDao.insert() → UNIQUE 위반이면 DUPLICATE
 * - 1순위에서는 학생회비 납부 여부 확인을 건너뜀 (2순위)
 *
 * 설계: docs/design-server-api.md 4-3, 4-4
 */
@Service
public class ApplyService {
}
